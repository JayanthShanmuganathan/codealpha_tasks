# CodeAlpha_AIChatbot

![Java](https://img.shields.io/badge/Java-17%2B-orange?style=flat-square&logo=java)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing-blue?style=flat-square)
![NLP](https://img.shields.io/badge/NLP-Rule--Based-teal?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen?style=flat-square)
![Internship](https://img.shields.io/badge/CodeAlpha-Internship%20Task-purple?style=flat-square)

An **AI-powered Chatbot** built in Java with a dark-themed **Swing GUI**, as part of the **CodeAlpha Java Programming Internship**. Uses rule-based Natural Language Processing (NLP) techniques including tokenisation, intent matching, TF-style keyword scoring, and sentiment detection.

---

## Features

- Real-time interactive chat via a dark-themed Java Swing GUI
- NLP engine with 12+ trained intents (greetings, Java, AI, jokes, weather, maths, and more)
- Tokenisation — lowercases and splits user input into tokens
- TF-style scoring — counts keyword overlaps to find the best-matching intent
- Confidence threshold — falls back gracefully when no intent matches
- Sentiment detection — detects positive / negative tone and adjusts replies
- Built-in maths evaluator — handles expressions like `25 * 4`, `100 / 5`
- Quick-reply chips for one-click suggestions
- 400 ms typing delay for a natural feel
- Fully standalone — no external libraries or internet connection required

---

## NLP Techniques Used

| Technique              | Description                                                       |
|------------------------|-------------------------------------------------------------------|
| Tokenisation           | Input lowercased and split on non-alphabetic characters           |
| Intent Matching        | 12 intents, each with keyword lists and multiple reply variants   |
| TF-style Scoring       | Counts how many tokens match each intent's keyword list           |
| Confidence Threshold   | Only picks an intent if score >= 1, else uses a fallback reply    |
| Sentiment Detection    | Positive / negative word lists adjust the tone of the reply       |
| Maths Evaluation       | Regex-based parser for +, -, *, / expressions                     |
| Randomised Responses   | Multiple reply variants per intent to feel less repetitive        |

---

## Trained Intents / Knowledge Base

| Intent        | Example Input                   |
|---------------|---------------------------------|
| greeting      | "Hi", "Hello", "Hey"            |
| farewell      | "Bye", "Goodbye", "See you"     |
| how_are_you   | "How are you?", "How's it going?"|
| identity      | "Who are you?", "What is your name?" |
| java          | "What is Java?", "Tell me about JVM" |
| ai_ml         | "What is AI?", "Explain NLP"    |
| help          | "Help me", "What can you do?"   |
| time_date     | "What time is it?", "Today's date" |
| weather       | "What's the weather like?"      |
| joke          | "Tell me a joke", "Say something funny" |
| math          | "5 + 3", "25 * 4", "100 / 5"   |
| thanks        | "Thank you", "Thanks a lot"     |

---

## Tech Stack

- **Language:** Java (JDK 8+)
- **GUI:** Java Swing (`JFrame`, `JTextPane`, `JTextField`, `JButton`)
- **NLP:** Rule-based engine (custom, no external library)
- **No external dependencies required**

---

## Getting Started

### Prerequisites

- Java JDK 8 or above
- A terminal or command prompt

### Installation

```bash
# 1. Clone the repository
git clone https://github.com/YOUR_USERNAME/codealpha_tasks.git

# 2. Navigate to the project folder
cd codealpha_tasks/CodeAlpha_AIChatbot

# 3. Compile
javac AIChatbot.java

# 4. Run
java AIChatbot
```

> **Windows users:** if you see an encoding error, compile with:
> ```bash
> javac -encoding UTF-8 AIChatbot.java
> ```

---

## Project Structure

```
CodeAlpha_AIChatbot/
├── AIChatbot.java    # Full source — NLP engine + Swing GUI
└── README.md         # Project documentation
```

---

## GUI Preview

```
+--------------------------------------------------+
|  [ON]  JavaBot    NLP-powered  rule-based AI     |
+--------------------------------------------------+
|                                                  |
|  JavaBot                                         |
|  Hello! I'm JavaBot. Ask me about Java, AI...    |
|                                                  |
|  You                                             |
|  Tell me a joke                                  |
|                                                  |
|  JavaBot                                         |
|  Why do Java devs wear glasses?                  |
|  Because they don't C#!                          |
|                                                  |
+--------------------------------------------------+
|  [Hi!] [Tell me a joke] [What is Java?] [Help]  |
+--------------------------------------------------+
|  [ Type a message...              ]  [ Send ]    |
+--------------------------------------------------+
```

---

## Internship Details

| Field       | Details                        |
|-------------|--------------------------------|
| Internship  | CodeAlpha Java Programming     |
| Task        | Artificial Intelligence Chatbot|
| Domain      | Java / AI / NLP                |

---

## License

This project is licensed under the [MIT License](LICENSE).

---

*Built with Java Swing + custom NLP as part of the CodeAlpha Internship Program.*
