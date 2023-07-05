package de.ait.gethelp.services.impl;

import de.ait.gethelp.dto.CardDto;
import de.ait.gethelp.dto.CardsPage;
import de.ait.gethelp.dto.NewCardDto;
import de.ait.gethelp.exceptions.BadDataException;
import de.ait.gethelp.exceptions.NotFoundException;
import de.ait.gethelp.models.Card;
import de.ait.gethelp.models.Category;
import de.ait.gethelp.models.SubCategory;
import de.ait.gethelp.models.User;
import de.ait.gethelp.repositories.CardsRepository;
import de.ait.gethelp.repositories.CategoriesRepository;
import de.ait.gethelp.repositories.SubCategoriesRepository;
import de.ait.gethelp.repositories.UsersRepository;
import de.ait.gethelp.services.CardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static de.ait.gethelp.dto.CardDto.from;

@RequiredArgsConstructor
@Service
public class CardsServiceImpl implements CardsService {

    private final CardsRepository cardsRepository;
    private final CategoriesRepository categoriesRepository;
    private final SubCategoriesRepository subCategoriesRepository;
    private final UsersRepository usersRepository;

    @Override
    public CardsPage getAll() {
        return CardsPage.builder()
                .cards(from(cardsRepository.findAll()))
                .build();
    }

    @Override
    public CardDto getById(Long cardId) {
        Card card = cardsRepository.findById(cardId).orElseThrow(
                () -> new NotFoundException("Card <" + cardId + "> not found"));
        return CardDto.from(card);
    }

    @Override
    public CardDto addCard(Long currentUserId, NewCardDto newCard) {
        User user = usersRepository.findById(currentUserId)
                .orElseThrow(IllegalArgumentException::new);
        Category category = categoriesRepository.findById(newCard.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Category <" + newCard.getCategoryId() + "> not found"));
        SubCategory subCategory = subCategoriesRepository.findById(newCard.getSubCategoryId()).orElseThrow(
                () -> new NotFoundException("SubCategory <" + newCard.getSubCategoryId() + "> not found"));
        if (!subCategory.getCategory().getId().equals(category.getId())) {
            throw new BadDataException("SubCategory doesn't match to this Category");
        }
        if (newCard.getTitle().length() > Card.DatabaseConstraints.CARD_TITLE_LENGTH) {
            throw new BadDataException("Length of Title field shouldn't be more than " + Card.DatabaseConstraints.CARD_TITLE_LENGTH + " characters.");
        }
        if (newCard.getDescription().length() > Card.DatabaseConstraints.CARD_DESCRIPTION_LENGTH) {
            throw new BadDataException("Length of Description field shouldn't be more than " + Card.DatabaseConstraints.CARD_DESCRIPTION_LENGTH + " characters.");
        }
        if (newCard.getFullDescription().length() > Card.DatabaseConstraints.CARD_FULL_DESCRIPTION_LENGTH) {
            throw new BadDataException("Length of FullDescription field shouldn't be more than " + Card.DatabaseConstraints.CARD_FULL_DESCRIPTION_LENGTH + " characters.");
        }
        Card card = Card.builder()
                .createdAt(LocalDateTime.now())
                .user(user)
                .title(newCard.getTitle())
                .category(category)
                .subcategory(subCategory)
                .price(newCard.getPrice())
                .description(newCard.getDescription())
                .fullDescription(newCard.getFullDescription())
                .isActive(true)
                .build();

        // check if User has status isHelper == false, then switch to true
        if (!user.getIsHelper()) {
            user.setIsHelper(true);
            usersRepository.save(user);
        }
        cardsRepository.save(card);
        return CardDto.from(card);
    }

    @Override
    public CardDto editCard(Long currentUserId, Long cardId, NewCardDto editedCard) {
        User user = usersRepository.findById(currentUserId)
                .orElseThrow(IllegalArgumentException::new);
        Category category = categoriesRepository.findById(editedCard.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Category <" + editedCard.getCategoryId() + "> not found"));
        SubCategory subCategory = subCategoriesRepository.findById(editedCard.getSubCategoryId()).orElseThrow(
                () -> new NotFoundException("SubCategory <" + editedCard.getSubCategoryId() + "> not found"));
        Card card = cardsRepository.findById(cardId).orElseThrow(
                () -> new NotFoundException("Card <" + cardId + "> not found"));
        card.setTitle(editedCard.getTitle());
        card.setUser(user);
        card.setCategory(category);
        card.setSubcategory(subCategory);
        card.setPrice(editedCard.getPrice());
        card.setDescription(editedCard.getDescription());
        card.setFullDescription(editedCard.getFullDescription());
        cardsRepository.save(card);
        return CardDto.from(card);
    }

    @Override
    public CardDto deleteCard(Long cardId) {
        Card card = cardsRepository.findById(cardId).orElseThrow(
                () -> new NotFoundException("Card <" + cardId + "> not found"));
        cardsRepository.delete(card);
        return CardDto.from(card);
    }
}
