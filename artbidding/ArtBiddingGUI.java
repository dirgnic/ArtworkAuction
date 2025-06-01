package artbidding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArtBiddingGUI {
    private JFrame frame;
    private JTextArea outputArea;

    public ArtBiddingGUI() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Art Bidding Platform");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton btnRegisterArtist = new JButton("Register Artist");
        JButton btnAddArtwork = new JButton("Add Artwork");
        JButton btnListArtworks = new JButton("List Artworks");

        buttonPanel.add(btnRegisterArtist);
        buttonPanel.add(btnAddArtwork);
        buttonPanel.add(btnListArtworks);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(panel);

        btnRegisterArtist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "nume artist:");
                String email = JOptionPane.showInputDialog(frame, "email artist:");
                if (name != null && email != null) {
                    try {
                        Artist artist = new Artist(name, email);
                        UserService.getInstance().createUser(artist);
                        outputArea.append("artist înregistrat în DB: " + name + " (id: " + artist.getId() + ")\n");
                    } catch (Exception ex) {
                        outputArea.append("Eroare la înregistrare artist: " + ex.getMessage() + "\n");
                    }
                }
            }
        });

        btnAddArtwork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String artistIdStr = JOptionPane.showInputDialog(frame, "ID artist pentru operă:");
                String title = JOptionPane.showInputDialog(frame, "titlu operă:");
                String priceStr = JOptionPane.showInputDialog(frame, "preț de pornire:");
                if (artistIdStr != null && title != null && priceStr != null) {
                    try {
                        int artistId = Integer.parseInt(artistIdStr);
                        double price = Double.parseDouble(priceStr);
                        User artistUser = UserService.getInstance().getUser(artistId);
                        if (artistUser instanceof Artist) {
                            Artwork art = new Artwork(title, (Artist) artistUser, price);
                            ArtworkService.getInstance().createArtwork(art);
                            outputArea.append("operă adăugată în DB: " + title + " (id: " + art.getId() + ")\n");
                        } else {
                            outputArea.append("ID-ul nu aparține unui artist.\n");
                        }
                    } catch (Exception ex) {
                        outputArea.append("Eroare la adăugare operă: " + ex.getMessage() + "\n");
                    }
                }
            }
        });

        btnListArtworks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea.append("lista operelor din DB:\n");
                try {
                    for (Artwork a : ArtworkService.getInstance().getAllArtworks(UserService.getInstance())) {
                        outputArea.append(a.toString() + "\n");
                    }
                } catch (Exception ex) {
                    outputArea.append("Eroare la listare opere: " + ex.getMessage() + "\n");
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ArtBiddingGUI();
    }
}
