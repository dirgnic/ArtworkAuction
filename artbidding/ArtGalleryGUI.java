package artbidding;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ArtGalleryGUI extends JFrame {
    private JPanel galleryPanel;

    public ArtGalleryGUI() {
        setTitle("AI Art Gallery");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        galleryPanel = new JPanel();
        galleryPanel.setLayout(new GridLayout(0, 3, 20, 20));
        JScrollPane scrollPane = new JScrollPane(galleryPanel);
        add(scrollPane, BorderLayout.CENTER);

        loadArtworks();

        setVisible(true);
    }

    private void loadArtworks() {
        try {
            List<Artwork> artworks = ArtworkService.getInstance().getAllArtworks(UserService.getInstance());
            for (Artwork art : artworks) {
                JPanel artPanel = new JPanel();
                artPanel.setLayout(new BorderLayout());

                // Use an actual AI image prompt for the artwork title
                // Example prompt for an AI image API: "A painting of {title} in a modern art style"
                String prompt = "A painting of " + art.getTitle() + " in a modern art style";
                String encodedPrompt = URLEncoder.encode(prompt, StandardCharsets.UTF_8);
                // Example: Replace with your AI image API endpoint if available
                // String imageUrl = "https://your-ai-image-api.com/generate?prompt=" + encodedPrompt;
                // For demo, fallback to Unsplash with prompt as query
                String imageUrl = "https://source.unsplash.com/400x300/?" + encodedPrompt;

                JLabel imageLabel = new JLabel();
                try {
                    ImageIcon icon = new ImageIcon(new URL(imageUrl));
                    Image img = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(img));
                } catch (Exception e) {
                    imageLabel.setText("[image]");
                }
                JLabel titleLabel = new JLabel(art.getTitle(), SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

                artPanel.add(imageLabel, BorderLayout.CENTER);
                artPanel.add(titleLabel, BorderLayout.SOUTH);
                artPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                galleryPanel.add(artPanel);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Eroare la încărcarea operelor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ArtGalleryGUI::new);
    }
}
