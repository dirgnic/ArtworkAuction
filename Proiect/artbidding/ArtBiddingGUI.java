package artbidding;

import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArtBiddingGUI {
    private JFrame frame;
    private JTextArea outputArea;
    private AuctionService service;

    public ArtBiddingGUI() {
        service = new AuctionService();
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Art Bidding Platform");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // zona de output
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // butoane pentru acțiuni simple
        JPanel buttonPanel = new JPanel();

        JButton btnRegisterArtist = new JButton("Register Artist");
        JButton btnAddArtwork = new JButton("Add Artwork");
        JButton btnListArtworks = new JButton("List Artworks");

        buttonPanel.add(btnRegisterArtist);
        buttonPanel.add(btnAddArtwork);
        buttonPanel.add(btnListArtworks);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(panel);

        // acțiuni pe butoane
        /*
        btnRegisterArtist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Artist artist = neoiw Artist(1, "Leonardo", "leo@art.com");
                service.registerUser(artist);
                outputArea.append("artist înregistrat: " + artist.getName() + "\n");
            }
        }) */
        btnRegisterArtist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "nume artist:");
                String email = JOptionPane.showInputDialog(frame, "email artist:");
                if (name != null && email != null) {
                    Artist artist = new Artist(name, email);
                    service.registerUser(artist);
                    outputArea.append("artist înregistrat: " + name + "\n");
                }
            }
        });
        /*
        btnAddArtwork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = service.getUserById(1);
                if (user instanceof Artist) {
                    Artist artist = (Artist) user;
                    Artwork art = new Artwork("Mona Lisa", artist, 1000);
                    service.addArtwork(art);
                    outputArea.append("operă adăugată: " + art.getTitle() + "\n");
                } else {
                    outputArea.append("artistul nu este înregistrat.\n");
                }
            }
        });

         */

        btnAddArtwork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = service.getUserById(1);
                if (user instanceof Artist) {
                    String title = JOptionPane.showInputDialog(frame, "titlu operă:");
                    String priceStr = JOptionPane.showInputDialog(frame, "preț de pornire:");
                    if (title != null && priceStr != null) {
                        try {
                            double price = Double.parseDouble(priceStr);
                            Artist artist = (Artist) user;
                            Artwork art = new Artwork(title, artist, price);
                            service.addArtwork(art);
                            outputArea.append("operă adăugată: " + title + "\n");
                        } catch (NumberFormatException ex) {
                            outputArea.append("preț invalid.\n");
                        }
                    }
                } else {
                    outputArea.append("artistul nu este înregistrat.\n");
                }
            }
        });


        btnListArtworks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea.append("lista operelor:\n");
                for (Artwork a : service.getArtworks()) {
                    outputArea.append(a.toString() + "\n");
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ArtBiddingGUI();
    }
}
