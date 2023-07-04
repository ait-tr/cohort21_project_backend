package de.ait.gethelp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cards")
public class Card {

    public static class DatabaseConstraints {
        public static final int CARD_TITLE_LENGTH = 45;
        public static final int CARD_DESCRIPTION_LENGTH = 170;
        public static final int CARD_FULL_DESCRIPTION_LENGTH = 5000;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "title", length = DatabaseConstraints.CARD_TITLE_LENGTH)
    private String title;

    // @Column(name = "image")
    // TODO private Image/String image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private SubCategory subcategory;

    @Column(name = "price")
    private Double price;

    @Column(name = "description", length = DatabaseConstraints.CARD_DESCRIPTION_LENGTH)
    private String description;

    @Column(name = "full_description", length = DatabaseConstraints.CARD_FULL_DESCRIPTION_LENGTH)
    private String fullDescription;

    @Column(name = "isActive")
    private Boolean isActive;
}
