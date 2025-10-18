# How to Run FlowScript IDE

## Method 1: Using Maven (Recommended)
```bash
mvn javafx:run
```

## Method 2: Using IntelliJ IDEA

### Step 1: Configure Run Configuration
1. Go to **Run → Edit Configurations...**
2. Create new **Application** configuration
3. Set **Main class**: `com.flowscript.ide.FlowScriptIDE`
4. Set **VM options**:
```
--module-path /Users/caferrerb/.m2/repository/org/openjfx/javafx-controls/21.0.1/javafx-controls-21.0.1-mac.jar:/Users/caferrerb/.m2/repository/org/openjfx/javafx-fxml/21.0.1/javafx-fxml-21.0.1-mac.jar:/Users/caferrerb/.m2/repository/org/openjfx/javafx-base/21.0.1/javafx-base-21.0.1-mac.jar:/Users/caferrerb/.m2/repository/org/openjfx/javafx-graphics/21.0.1/javafx-graphics-21.0.1-mac.jar --add-modules javafx.controls,javafx.fxml
```

### Alternative VM options (simpler):
```
--add-modules javafx.controls,javafx.fxml
```

## Method 3: Command Line with JavaFX
```bash
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp target/classes com.flowscript.ide.FlowScriptIDE
```

## Method 4: Using the Fat JAR
```bash
mvn clean package
java -jar target/flowscript-1.0.0-jar-with-dependencies.jar
```

---

## Features Available in the GUI

### 🎯 **Token Table Analysis**
- **Real-time tokenization** of FlowScript code
- **Color-coded tokens** by category:
  - **Purple**: Keywords (function, process, if, etc.)
  - **Orange**: Operators (+, -, ==, etc.)
  - **Green**: Literals (strings, numbers, booleans)
  - **Blue**: Identifiers (variable names)
  - **Gray**: Comments
  - **Red**: Delimiters ({}, [], (), etc.)

### 📝 **Code Editor**
- **Syntax highlighting** for FlowScript
- **Line numbers**
- **Auto-indentation**

### 📊 **Statistics**
- Token count by category
- Line count
- Character count
- Real-time error reporting

## Example FlowScript File

Load the comprehensive example:
```
example-flowscript.flow (648 lines, 2,333 tokens)
```

This example demonstrates:
- **8 complex functions**
- **2 business processes**
- **E-commerce order processing**
- **Complete error handling**
- **Database integration**
- **HTTP API calls**
- **All FlowScript language features**