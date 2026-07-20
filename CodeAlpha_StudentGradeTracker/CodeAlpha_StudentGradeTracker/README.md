# CodeAlpha_StudentGradeTracker

![Java](https://img.shields.io/badge/Java-17%2B-orange?style=flat-square&logo=java)
![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen?style=flat-square)
![Internship](https://img.shields.io/badge/CodeAlpha-Internship%20Task-purple?style=flat-square)

A console-based **Student Grade Tracker** built in Java as part of the **CodeAlpha Java Programming Internship**. It allows users to input, manage, and analyze student grades using `ArrayList`, with a clean summary report.

---

## Features

- Add students with name, subject, and score (0–100)
- Auto-assign student IDs (S001, S002 ...)
- Remove a student by ID
- List all students in a formatted table
- Group students by subject with per-subject statistics
- Summary report showing:
  - Class average
  - Highest and lowest scores
  - Pass / fail count (pass = score >= 50)
  - Grade distribution (A+, A, B, C, D, F)
- Built-in maths expression evaluator (e.g. `25 * 4`)
- Load sample data for quick testing

---

## Grade Scale

| Score     | Grade |
|-----------|-------|
| 90 – 100  | A+    |
| 80 – 89   | A     |
| 70 – 79   | B     |
| 60 – 69   | C     |
| 50 – 59   | D     |
| 0  – 49   | F     |

---

## Tech Stack

- **Language:** Java (JDK 8+)
- **Data Structure:** `ArrayList<Student>`
- **Interface:** Console / Terminal (Scanner-based menu)
- **No external libraries required**

---

## Getting Started

### Prerequisites

- Java JDK 8 or above installed
- A terminal or command prompt

### Installation

```bash
# 1. Clone the repository
git clone https://github.com/YOUR_USERNAME/codealpha_tasks.git

# 2. Navigate to the project folder
cd codealpha_tasks/CodeAlpha_StudentGradeTracker

# 3. Compile
javac StudentGradeTracker.java

# 4. Run
java StudentGradeTracker
```

---

## Usage

```
  +==============================+
  |   Student Grade Tracker      |
  +==============================+
  |  1. Add student              |
  |  2. Remove student           |
  |  3. List all students        |
  |  4. List by subject          |
  |  5. Show summary report      |
  |  6. Load sample data         |
  |  0. Exit                     |
  +==============================+
```

---

## Project Structure

```
CodeAlpha_StudentGradeTracker/
├── StudentGradeTracker.java    # Main source file
└── README.md                   # Project documentation
```

---

## Sample Output

```
  ID     Name                 Subject         Score  Grade
  --------------------------------------------------------------
  S001   Arjun Sharma         Mathematics      88.0  A
  S002   Priya Nair           Science          92.0  A+
  S003   Ravi Kumar           Mathematics      74.0  B
  ...

  +-----------------------------------+
  |         Summary Report            |
  +-----------------------------------+
  Total students : 10
  Class average  : 73.5
  Highest score  : 92.0  (Priya Nair - Science)
  Lowest score   : 45.0  (Suresh Babu - Mathematics)
  Passing (>=50) : 9 / 10

  Grade distribution:
    A+ : 1 student
    A  : 2 students
    B  : 3 students
    ...
```

---

## Internship Details

| Field       | Details                        |
|-------------|--------------------------------|
| Internship  | CodeAlpha Java Programming     |
| Task        | Student Grade Tracker          |
| Domain      | Java Development               |

---

## License

This project is licensed under the [MIT License](LICENSE).

---

*Built with Java as part of the CodeAlpha Internship Program.*
