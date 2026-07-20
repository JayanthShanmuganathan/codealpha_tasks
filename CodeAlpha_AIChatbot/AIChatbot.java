import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * AI Chatbot -- Java Swing GUI
 *
 * NLP techniques used:
 *   - Tokenisation & lowercasing
 *   - Keyword / intent matching (rule-based NLP)
 *   - TF-style scoring: count matching tokens per intent
 *   - Confidence threshold with fallback response
 *   - Sentiment detection (positive / negative words)
 *   - Small-talk & FAQ knowledge base
 *
 * Compile:  javac AIChatbot.java
 * Run:      java AIChatbot
 */
public class AIChatbot {

    // ------ NLP Engine ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    static class NLPEngine {

        static class Intent {
            final String label;
            final String[] keywords;
            final String[] responses;

            Intent(String label, String[] keywords, String[] responses) {
                this.label     = label;
                this.keywords  = keywords;
                this.responses = responses;
            }
        }

        private final List<Intent> intents = new ArrayList<>();
        private final Random rng = new Random();

        private static final Set<String> POS_WORDS = new HashSet<>(Arrays.asList(
            "good","great","excellent","awesome","nice","love","happy","best",
            "wonderful","fantastic","amazing","thank","thanks","perfect","cool"));
        private static final Set<String> NEG_WORDS = new HashSet<>(Arrays.asList(
            "bad","terrible","awful","hate","worst","horrible","poor","wrong",
            "broken","error","fail","failed","problem","issue","bug","crash"));

        NLPEngine() { loadKnowledgeBase(); }

        private void loadKnowledgeBase() {
            addIntent("greeting",
                new String[]{"hi","hello","hey","howdy","greetings","sup","yo"},
                new String[]{
                    "Hello! How can I help you today?",
                    "Hi there! What can I do for you?",
                    "Hey! Great to see you. What's on your mind?"
                });

            addIntent("farewell",
                new String[]{"bye","goodbye","see","later","exit","quit","cya","farewell"},
                new String[]{
                    "Goodbye! Have a wonderful day!",
                    "See you later! Take care.",
                    "Bye! Feel free to come back anytime."
                });

            addIntent("how_are_you",
                new String[]{"how","are","you","doing","feeling","going","status"},
                new String[]{
                    "I'm doing great, thanks for asking! How about you?",
                    "All systems running smoothly! What can I help with?",
                    "I'm fantastic! Ready to assist you."
                });

            addIntent("identity",
                new String[]{"name","who","what","are","you","called","bot","chatbot","ai","robot"},
                new String[]{
                    "I'm JavaBot, your AI-powered assistant built in Java!",
                    "My name is JavaBot. I'm an AI chatbot written in Java with NLP logic.",
                    "I'm JavaBot -- a rule-based NLP chatbot. Nice to meet you!"
                });

            addIntent("java",
                new String[]{"java","code","program","programming","software","compile","jdk","jvm","class","object"},
                new String[]{
                    "Java is a powerful, object-oriented language. What would you like to know?",
                    "Java runs on the JVM, making it platform-independent. Anything specific?",
                    "Java is great for building chatbots like me! Need help with any concept?"
                });

            addIntent("ai_ml",
                new String[]{"ai","artificial","intelligence","machine","learning","ml","deep","neural","nlp","model"},
                new String[]{
                    "AI and ML are fascinating fields! I use rule-based NLP myself.",
                    "Machine learning lets computers learn from data. Anything specific you'd like to know?",
                    "Natural Language Processing helps computers understand human language!"
                });

            addIntent("help",
                new String[]{"help","support","assist","guide","tutorial","how","explain","tell","show"},
                new String[]{
                    "Sure! You can ask me about Java, AI, or just chat. What do you need?",
                    "I'm here to help! Try asking about Java, NLP, or any general questions.",
                    "Happy to assist! Ask me anything -- programming, AI, or general topics."
                });

            addIntent("time_date",
                new String[]{"time","date","today","now","current","day","year","month","clock"},
                new String[]{
                    "The current date and time is: " + new Date(),
                });

            addIntent("weather",
                new String[]{"weather","rain","sunny","temperature","forecast","hot","cold","wind","climate"},
                new String[]{
                    "I don't have live weather data, but you can check weather.com for the latest forecast!",
                    "For real-time weather, try a weather API or your phone's weather app!"
                });

            addIntent("joke",
                new String[]{"joke","funny","laugh","humour","humor","lol","haha","comedian"},
                new String[]{
                    "Why do Java developers wear glasses? Because they don't C#!",
                    "I told my computer I needed a break. Now it won't stop sending me Kit Kat ads.",
                    "Why was the JavaScript developer sad? Because he didn't know how to null his feelings.",
                    "A SQL query walks into a bar and asks two tables: Can I JOIN you?"
                });

            addIntent("math",
                new String[]{"math","calculate","sum","add","subtract","multiply","divide","number","formula","equation"},
                new String[]{
                    "I can do basic maths! Try typing something like '5 + 3' and I'll calculate it.",
                    "For calculations, type an expression like '10 * 4' or '100 / 5'.",
                });

            addIntent("thanks",
                new String[]{"thank","thanks","appreciate","grateful","cheers","awesome"},
                new String[]{
                    "You're welcome! Happy to help.",
                    "Anytime! That's what I'm here for.",
                    "Glad I could help! Anything else?"
                });
        }

        private void addIntent(String label, String[] keywords, String[] responses) {
            intents.add(new Intent(label, keywords, responses));
        }

        private String[] tokenise(String input) {
            return input.toLowerCase().split("[^a-z0-9]+");
        }

        private String tryMath(String input) {
            String clean = input.trim().replaceAll("\\s+", "");
            try {
                if (clean.matches("\\d+\\.?\\d*\\+\\d+\\.?\\d*")) {
                    String[] p = clean.split("\\+");
                    return String.valueOf(Double.parseDouble(p[0]) + Double.parseDouble(p[1]));
                } else if (clean.matches("\\d+\\.?\\d*-\\d+\\.?\\d*")) {
                    String[] p = clean.split("-");
                    return String.valueOf(Double.parseDouble(p[0]) - Double.parseDouble(p[1]));
                } else if (clean.matches("\\d+\\.?\\d*\\*\\d+\\.?\\d*")) {
                    String[] p = clean.split("\\*");
                    return String.valueOf(Double.parseDouble(p[0]) * Double.parseDouble(p[1]));
                } else if (clean.matches("\\d+\\.?\\d*/\\d+\\.?\\d*")) {
                    String[] p = clean.split("/");
                    double divisor = Double.parseDouble(p[1]);
                    if (divisor == 0) return "Error: division by zero!";
                    return String.valueOf(Double.parseDouble(p[0]) / divisor);
                }
            } catch (Exception ignored) {}
            return null;
        }

        private String detectSentiment(String[] tokens) {
            int pos = 0, neg = 0;
            for (String t : tokens) {
                if (POS_WORDS.contains(t)) pos++;
                if (NEG_WORDS.contains(t)) neg++;
            }
            if (pos > neg) return "positive";
            if (neg > pos) return "negative";
            return "neutral";
        }

        public String respond(String userInput) {
            if (userInput == null || userInput.trim().isEmpty())
                return "Please type something so I can help!";

            String mathResult = tryMath(userInput.trim());
            if (mathResult != null)
                return "The answer is: " + mathResult;

            String[] tokens = tokenise(userInput);
            String sentiment = detectSentiment(tokens);

            Intent bestIntent = null;
            int bestScore = 0;

            for (Intent intent : intents) {
                int score = 0;
                for (String token : tokens)
                    for (String kw : intent.keywords)
                        if (token.equals(kw)) score++;
                if (score > bestScore) {
                    bestScore = score;
                    bestIntent = intent;
                }
            }

            if (bestIntent != null && bestScore >= 1) {
                String[] responses = bestIntent.responses;
                String reply = responses[rng.nextInt(responses.length)];
                if (sentiment.equals("positive") && bestScore == 1)
                    reply = "Glad you're feeling positive! " + reply;
                else if (sentiment.equals("negative") && bestScore == 1)
                    reply = "Sorry to hear that. " + reply;
                return reply;
            }

            String[] fallbacks = {
                "I'm not sure I understand. Could you rephrase?",
                "Hmm, that's an interesting one. Can you tell me more?",
                "I don't have an answer for that yet, but I'm learning!",
                "Could you clarify? I want to help but I'm not sure what you mean.",
                "That's beyond my current knowledge. Try asking about Java or AI!"
            };
            return fallbacks[rng.nextInt(fallbacks.length)];
        }
    }

    // ------ Swing GUI ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    static class ChatWindow extends JFrame {

        private final JTextPane chatPane;
        private final JTextField inputField;
        private final JButton sendButton;
        private final NLPEngine nlp = new NLPEngine();
        private final javax.swing.text.StyledDocument doc;

        private static final Color BG_DARK    = new Color(18,  18,  24);
        private static final Color BG_PANEL   = new Color(26,  26,  35);
        private static final Color BG_INPUT   = new Color(36,  36,  48);
        private static final Color ACCENT     = new Color(99, 155, 255);
        private static final Color BOT_BG     = new Color(36,  36,  52);
        private static final Color USER_BG    = new Color(45,  90, 160);
        private static final Color TEXT_LIGHT = new Color(220, 220, 230);
        private static final Color TEXT_MUTED = new Color(130, 130, 150);

        ChatWindow() {
            setTitle("JavaBot -- AI Chatbot");
            setSize(600, 700);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setBackground(BG_DARK);

            chatPane = new JTextPane();
            chatPane.setEditable(false);
            chatPane.setBackground(BG_DARK);
            chatPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            doc = chatPane.getStyledDocument();

            JScrollPane scroll = new JScrollPane(chatPane);
            scroll.setBorder(BorderFactory.createEmptyBorder());
            scroll.setBackground(BG_DARK);
            scroll.getViewport().setBackground(BG_DARK);

            // Header
            JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 10));
            header.setBackground(BG_PANEL);
            header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(50, 50, 70)));

            JLabel statusDot = new JLabel("[ON]");
            statusDot.setFont(new Font("Monospaced", Font.BOLD, 11));
            statusDot.setForeground(new Color(80, 220, 120));

            JLabel title = new JLabel("JavaBot");
            title.setFont(new Font("SansSerif", Font.BOLD, 15));
            title.setForeground(TEXT_LIGHT);

            JLabel sub = new JLabel("NLP-powered  rule-based AI");
            sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
            sub.setForeground(TEXT_MUTED);

            header.add(statusDot);
            header.add(title);
            header.add(sub);

            // Input row
            inputField = new JTextField();
            inputField.setBackground(BG_INPUT);
            inputField.setForeground(TEXT_LIGHT);
            inputField.setCaretColor(ACCENT);
            inputField.setFont(new Font("SansSerif", Font.PLAIN, 14));
            inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 80), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

            sendButton = new JButton("Send");
            sendButton.setBackground(ACCENT);
            sendButton.setForeground(Color.WHITE);
            sendButton.setFont(new Font("SansSerif", Font.BOLD, 13));
            sendButton.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
            sendButton.setFocusPainted(false);
            sendButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JPanel inputRow = new JPanel(new BorderLayout(8, 0));
            inputRow.setBackground(BG_PANEL);
            inputRow.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(50, 50, 70)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
            inputRow.add(inputField, BorderLayout.CENTER);
            inputRow.add(sendButton, BorderLayout.EAST);

            // Quick-reply chips
            JPanel chips = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
            chips.setBackground(BG_PANEL);
            chips.setBorder(BorderFactory.createEmptyBorder(0, 12, 4, 12));
            String[] suggestions = {"Hi!", "Tell me a joke", "What is Java?", "What's the time?", "Help"};
            for (String s : suggestions) {
                JButton chip = new JButton(s);
                chip.setFont(new Font("SansSerif", Font.PLAIN, 11));
                chip.setBackground(BG_INPUT);
                chip.setForeground(TEXT_MUTED);
                chip.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(60, 60, 80), 1),
                    BorderFactory.createEmptyBorder(4, 10, 4, 10)));
                chip.setFocusPainted(false);
                chip.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                chip.addActionListener(e -> sendMessage(s));
                chips.add(chip);
            }

            JPanel bottom = new JPanel(new BorderLayout());
            bottom.setBackground(BG_PANEL);
            bottom.add(chips, BorderLayout.NORTH);
            bottom.add(inputRow, BorderLayout.SOUTH);

            setLayout(new BorderLayout());
            add(header, BorderLayout.NORTH);
            add(scroll, BorderLayout.CENTER);
            add(bottom, BorderLayout.SOUTH);

            sendButton.addActionListener(e -> sendMessage(inputField.getText()));
            inputField.addActionListener(e -> sendMessage(inputField.getText()));

            appendBot("Hello! I'm JavaBot. Ask me about Java, AI, maths, or just say hi!");
        }

        private void sendMessage(String text) {
            text = text.trim();
            if (text.isEmpty()) return;
            appendUser(text);
            inputField.setText("");
            final String userText = text;
            javax.swing.Timer timer = new javax.swing.Timer(400, e -> {
                String reply = nlp.respond(userText);
                appendBot(reply);
            });
            timer.setRepeats(false);
            timer.start();
        }

        private void appendUser(String text) {
            appendMessage("You", text, USER_BG, TEXT_LIGHT);
        }

        private void appendBot(String text) {
            appendMessage("JavaBot", text, BOT_BG, TEXT_LIGHT);
        }

        private void appendMessage(String sender, String text, Color bg, Color fg) {
            try {
                javax.swing.text.Style base = chatPane.addStyle("base", null);
                javax.swing.text.StyleConstants.setFontFamily(base, "SansSerif");

                javax.swing.text.Style labelStyle = chatPane.addStyle("label_" + System.nanoTime(), base);
                javax.swing.text.StyleConstants.setFontSize(labelStyle, 10);
                javax.swing.text.StyleConstants.setForeground(labelStyle, TEXT_MUTED);
                javax.swing.text.StyleConstants.setBold(labelStyle, true);

                javax.swing.text.Style msgStyle = chatPane.addStyle("msg_" + System.nanoTime(), base);
                javax.swing.text.StyleConstants.setFontSize(msgStyle, 13);
                javax.swing.text.StyleConstants.setForeground(msgStyle, fg);
                javax.swing.text.StyleConstants.setBackground(msgStyle, bg);
                javax.swing.text.StyleConstants.setSpaceAbove(msgStyle, 2);
                javax.swing.text.StyleConstants.setSpaceBelow(msgStyle, 2);

                int offset = doc.getLength();
                doc.insertString(offset, (offset > 0 ? "\n" : "") + sender + "\n", labelStyle);
                doc.insertString(doc.getLength(), "  " + text + "\n", msgStyle);
                chatPane.setCaretPosition(doc.getLength());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // ------ Main ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new ChatWindow().setVisible(true);
        });
    }
}
