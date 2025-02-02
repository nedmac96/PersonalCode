import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from time import time


class Model:
    def __init__(self, model_func, features):
        self.model_func = model_func
        self.features = features
        self.predict_func = None
        self.model_info = ""

    def fit(self, df_sample):
        self.predict_func, self.model_info = self.model_func(df_sample, self.features)

    def predict(self, row):
        if self.predict_func:
            # return np.sum([row[feature] * weight for feature, weight in zip(self.features, self.feature_weights)]) + self.const
            return self.predict_func(row)
        else:
            return None


def test_model(model, df_sample):
    train_df = df_sample.sample(frac=0.8, random_state=42)
    test_df = df_sample.drop(train_df.index)
    print("Training Data:")
    print(train_df)
    print("Testing Data:")
    print(test_df)
    model.fit(train_df)
    print(
        f"Performance on training set: {get_error(train_df, model=model):.5f}")
    print(
        f"Performance on test set: {get_error(test_df, model=model):.5f}")
    model.fit(df_sample)
    print("Model Info")
    print(model.model_info)

# Some form of GBM
def model1(df_sample, features):
    precision = 100
    weights = [0] * len(features)
    const = 0
    simple_df = pd.DataFrame()
    for i, feature in enumerate(features):
        simple_df[f"x{i}"] = df_sample.apply(feature, axis=1)
    simple_df["y"] = df_sample[y]
    y_range = simple_df["y"].max() - simple_df["y"].min()
    if y_range == 0:
        return weights
    for i in range(len(features)):
        x_range = simple_df[f"x{i}"].max() - simple_df[f"x{i}"].min()
        if x_range == 0:
            continue
        min_weight = -abs(y_range / x_range) * 1
        max_weight = abs(y_range / x_range) * 1
        feature_min_weight = min_weight
        feature_max_weight = max_weight
        for j in range(precision):
            mid_weight = (feature_max_weight + feature_min_weight) / 2
            weights[i] = (feature_max_weight + mid_weight) / 2
            upper_error, upper_const = get_error(simple_df, weights)
            weights[i] = mid_weight
            mid_error, mid_const = get_error(simple_df, weights)
            weights[i] = (mid_weight + feature_min_weight) / 2
            lower_error, lower_const = get_error(simple_df, weights)
            min_error = min(upper_error, mid_error, lower_error)
            if min_error == upper_error:
                feature_min_weight = mid_weight
                const = upper_const
            elif min_error == lower_error:
                feature_max_weight = mid_weight
                const = lower_const
            else:
                feature_min_weight = (mid_weight + feature_min_weight) / 2
                feature_max_weight = (feature_max_weight + mid_weight) / 2
                const = mid_const
        if feature_min_weight == min_weight or feature_max_weight == max_weight:
            print(f"Insufficient search space for {i}")
        weights[i] = (feature_max_weight + feature_min_weight) / 2
    return apply_weights(features, weights, const), f"Weights: {weights}, Constant: {const}"

def apply_weights(features, weights, const):
    return lambda row: np.sum([row[feature] * weight for feature, weight in zip(features, weights)]) + const

def get_error(df, weights=None, model=None):
    if weights:
        vals = sum(df[f"x{i}"] * weight for i, weight in enumerate(weights))
        const = df["y"].mean() - vals.mean()
        vals = vals + const
        return (abs(vals - df["y"])).sum() / len(df), const
    elif model:
        return sum(abs(df[y] - df.apply(lambda row: model.predict(row), axis=1))) / len(df)

# Solves constraint problem assuming two columns of ordered keys with unknown values
def model2(df):
    def recursive_adjust(data, main_key, key_type, type1_mins, type1_maxs, type2_mins, type2_maxs, min_val, max_val, i):
        if key_type == x1:
            type1_mins = {key: max(value, min_val) if key >= main_key else value for key, value in type1_mins.items()}
            type1_maxs = {key: min(value, max_val) if key <= main_key else value for key, value in type1_maxs.items()}
            data.loc[data[x1] == main_key, "type1_checked"] = True
            for row in data[data[x1] == main_key].iloc:
                if not row["type2_checked"]:
                    min_val2 = (round(row[y]) - 1) / max_val if max_val > 0 else float("inf")
                    max_val2 = (round(row[y]) + 1) / min_val if min_val > 0 else float("inf")
                    data, type1_mins, type1_maxs, type2_mins, type2_maxs = recursive_adjust(data, row[x2], x2, type1_mins, type1_maxs, type2_mins, type2_maxs, min_val2, max_val2, i + 1)
        else:
            type2_mins = {key: max(value, min_val) if key >= main_key else value for key, value in type2_mins.items()}
            type2_maxs = {key: min(value, max_val) if key <= main_key else value for key, value in type2_maxs.items()}
            data.loc[data[x2] == main_key, "type2_checked"] = True
            for row in data[data[x2] == main_key].iloc:
                if not row["type1_checked"]:
                    min_val2 = (round(row[y]) - 1) / max_val if max_val > 0 else float("inf")
                    max_val2 = (round(row[y]) + 1) / min_val if min_val > 0 else float("inf")
                    data, type1_mins, type1_maxs, type2_mins, type2_maxs = recursive_adjust(data, row[x1], x1, type1_mins, type1_maxs, type2_mins, type2_maxs, min_val2, max_val2, i + 1)
        return data, type1_mins, type1_maxs, type2_mins, type2_maxs

    data = df.sort_values(by=[x1, x2]).drop_duplicates()
    data["type1_checked"] = False
    data["type2_checked"] = False

    type1 = sorted(data[x1].unique())
    type2 = sorted(data[x2].unique())

    type1_mins = {t1: 0.0 for t1 in type1}
    type2_mins = {t2: 0.0 for t2 in type2}
    type1_maxs = {t1: float('inf') for t1 in type1}
    type2_maxs = {t2: float('inf') for t2 in type2}

    min_val = 1.0
    max_val = 1.0
    main_key_index = 0
    key_type = x1
    data, type1_mins, type1_maxs, type2_mins, type2_maxs = recursive_adjust(data, type1[main_key_index], key_type, type1_mins, type1_maxs, type2_mins, type2_maxs, min_val, max_val, 0)
    while not all(data["type1_checked"]) and main_key_index < len(type1) - 1:
        main_key_index += 1
        if not all(data[data[x1]==type1[main_key_index]]["type1_checked"]):
            data, type1_mins, type1_maxs, type2_mins, type2_maxs = recursive_adjust(data, type1[main_key_index], key_type, type1_mins, type1_maxs, type2_mins, type2_maxs, type1_mins[type1[main_key_index]], type1_maxs[type1[main_key_index]], 10 * main_key_index + 5)

    # Reiterate for final check
    val = 0
    for key in type1_mins:
        type1_mins[key] = max(val, type1_mins[key])
        val = type1_mins[key]
    val = float("inf")
    for key in reversed(type1_maxs.keys()):
        type1_maxs[key] = min(val, type1_maxs[key])
        val = type1_maxs[key]
    val = 0
    for key in type2_mins:
        type2_mins[key] = max(val, type2_mins[key])
        val = type2_mins[key]
    val = float("inf")
    for key in reversed(type2_maxs.keys()):
        type2_maxs[key] = min(val, type2_maxs[key])
        val = type2_maxs[key]
    for index, row in data.iterrows():
        type1_mins[row[x1]] = max(type1_mins[row[x1]], (round(row[y]) - 1) / type2_maxs[row[x2]]) if type2_maxs[row[x2]] > 0 else float("inf")
        type1_maxs[row[x1]] = min(type1_maxs[row[x1]], (round(row[y]) + 1) / type2_mins[row[x2]]) if type2_mins[row[x2]] > 0 else type1_maxs[row[x1]]
        type2_mins[row[x2]] = max(type2_mins[row[x2]], (round(row[y]) - 1) / type1_maxs[row[x1]]) if type1_maxs[row[x1]] > 0 else float("inf")
        type2_maxs[row[x2]] = min(type2_maxs[row[x2]], (round(row[y]) + 1) / type1_mins[row[x1]]) if type1_mins[row[x1]] > 0 else type2_maxs[row[x2]]
    val = 0
    for key in type1_mins:
        type1_mins[key] = max(val, type1_mins[key])
        val = type1_mins[key]
    val = float("inf")
    for key in reversed(type1_maxs.keys()):
        type1_maxs[key] = min(val, type1_maxs[key])
        val = type1_maxs[key]
    val = 0
    for key in type2_mins:
        type2_mins[key] = max(val, type2_mins[key])
        val = type2_mins[key]
    val = float("inf")
    for key in reversed(type2_maxs.keys()):
        type2_maxs[key] = min(val, type2_maxs[key])
        val = type2_maxs[key]

    type1_df = pd.DataFrame([type1_mins, type1_maxs]).T
    type2_df = pd.DataFrame([type2_mins, type2_maxs]).T
    type1_df[2] = ((type1_df[0] + type1_df[1]) / 2)
    type2_df[2] = ((type2_df[0] + type2_df[1]) / 2)
    print(type1_df)
    print(type2_df)
    for item in ((type1_df[0] + type1_df[1]) / 2):
        print(item)
    print()
    for item in ((type2_df[0] + type2_df[1]) / 2):
        print(item)

    fig, ax = plt.subplots()
    ax.plot(type1_df.index, type1_df[2], color='blue')
    ax.fill_between(type1_df.index, type1_df[1], type1_df[0], color='blue', alpha=0.5)
    plt.show()

    fig, ax = plt.subplots()
    ax.plot(type2_df.index, type2_df[2], color='blue')
    ax.fill_between(type2_df.index, type2_df[1], type2_df[0], color='blue', alpha=0.5)
    plt.show()

# Assumes the mean
def model3(df_sample, features):
    return lambda row: df_sample[y].mean(), f"Weights: {[0 for _ in features]}, Constant: {df_sample[y].mean()}"

# Custom model
def model4(df_sample, features):
    weights = [2.6, 0.9]
    const = (df_sample[y] - sum(df_sample[feature] * weight for feature, weight in zip(features, weights))).mean()
    return apply_weights(features, weights, const), f"Weights: {weights}, Constant: {const}"


# Linear regression
def model5(df_sample, features):
    values = df_sample[features].values
    values = np.c_[np.ones(values.shape[0]), values]
    values_t = values.T
    result = np.linalg.inv(np.dot(values_t, values)).dot(np.dot(values_t, df_sample[y].values))
    return apply_weights(features, list(result[1:]), result[0]), f"Weights: {list(result[1:])}, Constant: {result[0]}"

# Linear regression with gradient decent
def model6(df_sample, features):
    values = df_sample[features].values
    values = np.c_[np.ones(values.shape[0]), values]
    values_t = values.T
    m = len(df_sample)
    alpha = 0.0001  # Learning rate
    iterations = 100000  # Number of iterations
    result = np.zeros(values.shape[1])
    for _ in range(iterations):
        y_pred = values.dot(result)
        error = y_pred - df_sample[y].values
        gradient = (2 / m) * values_t.dot(error)
        result -= alpha * gradient
    return apply_weights(features, list(result[1:]), result[0]), f"Weights: {list(result[1:])}, Constant: {result[0]}"

# Simple Neural Net
def model7(df_sample, features):
    values = df_sample[features].values
    output = df_sample[y].values

    input_layer_size = len(features)  # Two input features
    hidden_layer_size = 2  # Two neurons in hidden layer
    output_layer_size = 1  # One output neuron

    # Random initialization of weights and biases
    np.random.seed(17)
    w1 = np.random.randn(input_layer_size, hidden_layer_size)  # Weights for hidden layer
    b1 = np.zeros((1, hidden_layer_size))  # Biases for hidden layer
    w2 = np.random.randn(hidden_layer_size, output_layer_size)  # Weights for output layer
    b2 = np.zeros((1, output_layer_size))  # Biases for output layer

    # Step 3: Define the activation functions
    # ReLU activation for hidden layer
    def relu(x):
        return np.maximum(0, x)

    # Linear activation for output layer (no transformation)
    def linear(x):
        return x

    # Step 4: Define the forward pass
    def forward(values):
        # Hidden layer: input -> hidden activation
        z1 = np.dot(values, w1) + b1
        a1 = relu(z1)  # ReLU activation
        # Output layer: hidden -> output
        z2 = np.dot(a1, w2) + b2
        a2 = linear(z2)  # Linear activation for output layer
        return a1, a2  # Return hidden layer output and final output

    # Step 5: Define the loss function (Mean Squared Error)
    def compute_loss(y_true, y_pred):
        # print("y_true")
        # print(y_true)
        # print("y_pred")
        # print(y_pred.flatten())
        return np.mean(np.abs(y_true - y_pred))

    # Step 6: Backpropagation to compute gradients and update weights
    def backward(values, output, a1, a2):
        m = values.shape[0]  # Number of examples

        # Output layer gradients (using chain rule)
        dz2 = np.sign(a2 - output.reshape(-1, 1))  # Derivative of loss wrt output (MAE)
        dw2 = (1 / m) * np.dot(a1.T, dz2)
        db2 = (1 / m) * np.sum(dz2, axis=0, keepdims=True)

        # Hidden layer gradients (using chain rule)
        da1 = np.dot(dz2, w2.T)  # Backpropagate the error to hidden layer
        dz1 = da1 * (a1 > 0)  # Derivative of ReLU activation
        dw1 = (1 / m) * np.dot(values.T, dz1)
        db1 = (1 / m) * np.sum(dz1, axis=0, keepdims=True)

        return dw1, db1, dw2, db2

    # Step 7: Gradient Descent to update weights and biases
    def update_parameters(w1, b1, w2, b2, dw1, db1, dw2, db2, learning_rate=0.01):
        w1 -= learning_rate * dw1
        b1 -= learning_rate * db1
        w2 -= learning_rate * dw2
        b2 -= learning_rate * db2
        return w1, b1, w2, b2

    # Step 8: Train the model
    epochs = 20000
    learning_rate = 0.00001

    for epoch in range(epochs):
        # Forward pass
        a1, a2 = forward(values)

        # Backward pass (compute gradients)
        dw1, db1, dw2, db2 = backward(values, output, a1, a2)

        # Update parameters (weights and biases)
        w1, b1, w2, b2 = update_parameters(w1, b1, w2, b2, dw1, db1, dw2, db2, learning_rate)
        # if epoch % (epochs / 10) == 0:
        #     loss = compute_loss(output, a2)
        #     print(f"Epoch: {epoch}, Loss: {loss}")
        #     pass

    # print("w1")
    # print(w1)
    # print("b1")
    # print(b1)
    # print("w2")
    # print(w2)
    # print("b2")
    # print(b2)
    ret_str = ""
    for i, feature in enumerate(features):
        if i == len(features) // 2:
            ret_str = f"{ret_str}\n{y} = [{' '.join(f'{v[0]:.2f}' for v in w2)}]max([{' '.join(f'{v:.2f}' for v in w1[i])}][{feature}] + [{b1[0][i]:.2f}], [0]) + {b2[0][0]:.2f}"
        else:
            ret_str = f"{ret_str}\n{' ' * len(y)}                  [{' '.join(f'{v:.2f}' for v in w1[i])}][{feature}] + [{b1[0][i]:.2f}], [0])"
    return lambda row: forward(row[features])[1][0][0], ret_str

def generate_data():
    data = pd.DataFrame()
    np.random.seed(20)
    num_points = 10000
    # data["x0"] = np.random.randint(0, 100, size=num_points)
    # data["x1"] = np.random.randint(0, 100, size=num_points)
    # data["x2"] = np.random.randint(0, 100, size=num_points) * 0.5 + np.random.randint(0, 100, size=num_points) * 0.5
    # data["x3"] = np.random.randint(0, 100, size=num_points) / 3 + np.random.randint(0, 100, size=num_points) / 3 + np.random.randint(0, 100, size=num_points) / 3
    # data["x4"] = np.random.normal(50, 29, num_points)
    # data["x5"] = np.random.normal(25, 15, num_points) / 2 + np.random.normal(75, 15, num_points) / 2
    # data["x6"] = np.append(np.random.normal(25, 15, int(np.floor(num_points / 2))), np.random.normal(75, 15, int(np.ceil(num_points / 2))))
    # data["x7"] = np.random.beta(3, 9, size=num_points) * 200
    # data["y"] = (np.where(data["x0"] > data["x1"], data["x0"], data["x1"]) + data["x2"] + data["x3"] + data["x4"] + data["x5"] + data["x6"]+ data["x7"]) / 7 + 5
    # data["x8"] = np.select([(data["y"] > 65) & (data["y"] % 2 < 1), (data["y"] > data["x0"]) & (data["y"] < 60), (data["y"] > 70) | (data["y"] % 3 < 2), data["y"] < 55, data["y"] % 3 >= 1], ["A", "B", "C", "D", "E"], default="F")
    # data["x9"] = np.random.pareto(100, size=num_points) * 5000
    # data["x10"] = np.random.standard_cauchy(size=num_points) * 10 + 50
    data["y"] = np.random.randint(0, 100, size=num_points)
    data["x0"] = np.random.randint(0, 100, size=num_points)
    data["x1"] = np.random.normal(50, 15, num_points)
    data["x2"] = np.select([data["x0"] > 75, data["x0"] < 25, (data["x0"] > 35) & (data["x0"] < 65)], ["A", "B", "C"], default="D")
    data["x3"] = np.select([data["x1"] > 60, data["x1"] < 40, (data["x1"] > 45) & (data["x1"] < 55)], ["A", "B", "C"], default="D")
    data["x4"] = np.select([(data["x0"] < data["y"]) & (data["x1"] > data["y"]), (data["x0"] > data["y"]) & (data["x1"] < data["y"]), (data["x0"] > data["y"]) & (data["x1"] > data["y"])], ["A", "B", "C"], default="D")
    data["x5"] = np.select([data["x2"] == data["x4"], data["x3"] == data["x4"], data["x4"] == "A"], ["A", "B", "C"], default="D")
    data["x6"] = np.select([data["x4"].isin(["A", "B"]), data["x4"] == "C", data["x4"] == "D"], [(data["x0"] + data["x1"]) / 2, np.where(data["x0"] < data["x1"], data["x0"], data["x1"]) / 2, (np.where(data["x0"] > data["x1"], data["x0"], data["x1"]) + 99) / 2])

    #data["y"] = data["x0"] * 0.2 + data["x1"] * 0.7 + data["x2"] * 0.05 + data["x3"] * 0.05
    # data = pd.DataFrame({
    #     "x0": [2, 5, 6, 8, 11, 13, 3, 4, 6, 9, 10, 12],
    #     "y": ["A", "B", "A", "C", "B", "A", "A", "A", "B", "C", "C", "B"]
    # })
    # return data[["x0", "x1", "y"]]
    return data

def plot_weights_error():
    result = pd.DataFrame({"X": [0.1 * i + 0.0 for i in range(10) for _ in range(10)], "Y": [0.1 * i + 0.0 for _ in range(10) for i in range(10)]})
    result["Z"] = result.apply(lambda row: get_error(data, [row["X"], row["Y"]])[0], axis=1)
    result = result.pivot(columns="Y", index="X", values="Z")
    plt.contourf(result.columns, result.index, result.values, levels=np.logspace(np.log10(result.values.min()), np.log10(result.values.max()), 10))
    plt.colorbar()
    plt.show()

def pdf(value, mean, variance):
    return np.exp(-((value - mean) ** 2 / (2 * variance))) / (np.sqrt(2 * np.pi * variance))

def analyze(df_sample, features):
    num_points = len(df_sample)
    def get_dist_type(col):
        if num_points < 15:
            return "Unknown"
        ret_str = ""
        min_val = df_sample[col].min()
        max_val = df_sample[col].max()
        total_range = 1 + max_val - min_val
        q1 = df_sample[col].quantile(0.25)
        median = df_sample[col].median()
        q3 = df_sample[col].quantile(0.75)
        iqr = q3 - q1
        mean = df_sample[col].mean()
        std = df_sample[col].std()
        q0 = df_sample[col].quantile(0.1)
        q4 = df_sample[col].quantile(0.9)
        q15 = df_sample[col].quantile(0.4)
        q25 = df_sample[col].quantile(0.6)
        if abs((median - min_val) - iqr) + abs((max_val - median) - iqr) + abs(total_range / 2 - iqr) < max(5 * iqr / np.sqrt(num_points), iqr / 10):
            ret_str = ret_str + "Uniform"
        if abs(median - mean) + abs(q1 - (mean - 0.674 * std)) + abs(q3 - (mean + 0.674 * std)) + abs(q0 - (mean - 1.28 * std)) + abs(q4 - (mean + 1.28 * std)) + abs(q15 - (mean - 0.253 * std)) + abs(q25 - (mean + 0.253 * std)) < 0.75 * iqr / np.sqrt(np.sqrt(num_points)):
            ret_str = ret_str + "Normal"
        elif abs(df_sample[col].skew()) >= 0.5 and abs(q1 - (mean - 0.674 * std)) + abs(q3 - (mean + 0.674 * std)) + abs(q0 - (mean - 1.28 * std)) + abs(q4 - (mean + 1.28 * std)) + abs(q15 - (mean - 0.253 * std)) + abs(q25 - (mean + 0.253 * std)) < 6 * iqr / np.sqrt(np.sqrt(num_points)):
            ret_str = ret_str + "Skewed"
        if not ret_str:
            ret_str = "Unknown"
        return ret_str

    # col and dep_col must be continuous
    def get_linear_r2(col, dep_col=y):
        values = df_sample[col].values
        output = df_sample[dep_col].values
        x_b = np.c_[np.ones(values.shape[0]), values]
        theta = np.linalg.inv(x_b.T.dot(x_b)).dot(x_b.T).dot(output)
        return (1 - np.sum((output - (values * theta[1] + theta[0])) ** 2) / np.sum((output - np.mean(output)) ** 2)) * 100

    # col is categorical dep_col is continuous
    def get_categorical_r2(col, dep_col=y):
        return (1 - np.sum((df_sample[dep_col] - (df_sample[col].map(df_sample.groupby(col)[dep_col].mean()))) ** 2) / np.sum((df_sample[dep_col] - df_sample[dep_col].mean()) ** 2)) * 100

    # dep_col is categorical col is continuous
    def get_reverse_categorical_r2(col, dep_col=y):
        grouped = df_sample[[col, dep_col]].groupby(dep_col)
        means = grouped[col].mean().to_dict()
        variance = grouped[col].var().to_dict()
        frequencies = (grouped[col].size() / num_points).to_dict()
        error_model = 0
        error_null = 0
        for index, row in df_sample[[col, dep_col]].iterrows():
            error_model += (1 - pdf(row[col], means[row[dep_col]], variance[row[dep_col]]) * frequencies[row[dep_col]] / sum(pdf(row[col], means[cat], variance[cat]) * frequencies[cat] for cat in grouped.groups.keys())) ** 2
            error_null += (1 - frequencies[row[dep_col]]) ** 2

        return (1 - (error_model / error_null)) * 100

    # col and dep_col must be categorical
    def get_cat_to_cat_r2(col, dep_col=y):
        dep_frequencies = df_sample.groupby(dep_col)[col].size()
        frequencies = df_sample.groupby(col)[col].size()
        both_frequencies = df_sample.groupby([col, dep_col]).size()
        model_values = both_frequencies.loc[df_sample[[col, dep_col]].values.tolist()].values
        col_frequencies = frequencies[df_sample[col].values].values
        dep_frequencies_values = dep_frequencies[df_sample[dep_col].values].values

        error_model = ((1 - model_values / col_frequencies) ** 2).sum()
        error_null = ((1 - dep_frequencies_values / num_points) ** 2).sum()
        return (1 - (error_model / error_null)) * 100

    print(f"{num_points} data points with features: {', '.join(f for f in features[:-1])} and {features[-1]} to predict {y}")
    if len(df_sample) < 30:
        print("Small number of data points - may be inaccurate")
    num_cols = [col for col in df_sample[features + [y]].columns if pd.api.types.is_float_dtype(df_sample[col]) or pd.api.types.is_integer_dtype(df_sample[col])]
    r2_vals = []
    if num_cols:
        # Skew > 0 - right tailed, < 0 left tailed
        # Tailedness > 0 - heavy tails, < 0 light tails
        r2_vals = [get_linear_r2(col) for col in num_cols] if pd.api.types.is_float_dtype(df_sample[y]) or pd.api.types.is_integer_dtype(df_sample[y]) else [get_reverse_categorical_r2(col) for col in num_cols]
        print(pd.DataFrame({
            "DType": [("Integer" if pd.api.types.is_integer_dtype(df_sample[col]) else "Float") + " " + str(df_sample[col].dtype) for col in df_sample[num_cols].columns],
            "Min": df_sample[num_cols].min(),
            "Q1": df_sample[num_cols].quantile(0.25),
            "Median": df_sample[num_cols].median(),
            "Q3": df_sample[num_cols].quantile(0.75),
            "Max": df_sample[num_cols].max(),
            "Mean": df_sample[num_cols].mean(),
            "Std": df_sample[num_cols].std(),
            "Skew": df_sample[num_cols].skew(),
            "Tailedness": df_sample[num_cols].kurt(),
            "Dist Type": [get_dist_type(col) for col in num_cols],
            "R^2": r2_vals,
            "Useful": ["No" if v < 200 / num_points else "Maybe" if v < 1000 / num_points else "Yes" for v in r2_vals],
        }))
    print()
    for col in df_sample[features + [y]].columns:
        if col not in num_cols:
            r2 = get_categorical_r2(col) if pd.api.types.is_float_dtype(df_sample[y]) or pd.api.types.is_integer_dtype(df_sample[y]) else get_cat_to_cat_r2(col)
            r2_vals.append(r2)
            print(f"{col}  {'String' if pd.api.types.is_object_dtype(df_sample[col]) else 'Unknown'} {df_sample[col].dtype}, {', '.join(f'{v}: {c}' for v, c in df_sample[col].value_counts(dropna=False).sort_index().items())}, R^2: {r2:.2f}, Useful: {'No' if r2 < 200 / num_points else 'Maybe' if r2 < 1000 / num_points else 'Yes'}")
    print()
    print(f"Correlation Matrix ( >{1000 / num_points:.2f} means correlated, >{200 / num_points:.2f} means possibly correlated, <{200 / num_points:.2f} means not correlated):")
    print("")
    df = pd.DataFrame(index=features, columns=features)
    for f1 in features + [y]:
        for f2 in features + [y]:
            if f1 == f2 and False:
                df.loc[f1, f2] = 100.00
            elif f1 in num_cols:
                if f2 in num_cols:
                    df.loc[f1, f2] = get_linear_r2(f1, f2)
                else:
                    df.loc[f1, f2] = get_reverse_categorical_r2(f1, f2)
            else:
                if f2 in num_cols:
                    df.loc[f1, f2] = get_categorical_r2(f1, f2)
                else:
                    df.loc[f1, f2] = get_cat_to_cat_r2(f1, f2)
    print(df)
    print()
    print("Noise Data")
    grouped = df_sample.groupby(features)
    print(f"Data Points: {num_points}, Unique Data Points: {df_sample.groupby(features + [y]).ngroups}, Unique Feature Values: {grouped.ngroups}, Unique {y} Values: {df_sample[y].nunique()}")
    print(f"Must Have Noise: {any(grouped[y].nunique() > 1)}")

def show_correlation(df_sample, col1, col2):
    col1_numeric = pd.api.types.is_float_dtype(df_sample[col1]) or pd.api.types.is_integer_dtype(df_sample[col1])
    col2_numeric = pd.api.types.is_float_dtype(df_sample[col2]) or pd.api.types.is_integer_dtype(df_sample[col2])
    freq_table = pd.crosstab(pd.cut(df_sample[col2], bins=10) if col2_numeric else df_sample[col2], pd.cut(df_sample[col1], bins=10) if col1_numeric else df_sample[col1]).iloc[::-1]
    print(freq_table)
    plt.imshow(freq_table.values, cmap="Greens", interpolation='nearest', aspect='auto')
    plt.colorbar(label='Frequency')
    plt.xlabel(col1)
    plt.ylabel(col2, rotation=0)
    plt.xticks(np.arange(len(freq_table.columns)), freq_table.columns.astype(str))
    if not col2_numeric:
        plt.yticks(np.arange(len(freq_table.index)), freq_table.index.astype(str))
    plt.show()

start_time = time()
pd.set_option('display.max_columns', 20)
pd.set_option('display.width', 200)
pd.set_option('display.float_format', '{:.2f}'.format)
pd.set_option('display.max_rows', 200)
y = "y"
data = generate_data()
# model = Model(model8, ["x0", "x1"])
# test_model(model, data)
#plot_weights_error()
analyze(data, list(data.drop(columns=y).columns))
# plt.hist(data["x8"], bins=100)
# plt.show()
print()
# print(data)
#show_correlation(data, "x3", "x5")
print("====================================================================================================================================")
print(f"Finished after {time() - start_time:.3f} seconds")