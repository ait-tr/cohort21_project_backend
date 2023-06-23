package de.ait.gethelp.services.impl;

import de.ait.gethelp.dto.CardDto;
import de.ait.gethelp.dto.CardsPage;
import de.ait.gethelp.dto.ProfileDto;
import de.ait.gethelp.exceptions.ForbiddenException;
import de.ait.gethelp.exceptions.NotFoundException;
import de.ait.gethelp.models.Card;
import de.ait.gethelp.models.User;
import de.ait.gethelp.repositories.CardsRepository;
import de.ait.gethelp.repositories.UsersRepository;
import de.ait.gethelp.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static de.ait.gethelp.dto.CardDto.from;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final CardsRepository cardsRepository;

    @Override
    public ProfileDto getProfile(Long currentUserId) {
        User user = usersRepository.findById(currentUserId)
                .orElseThrow(IllegalArgumentException::new);
        CardsPage userCards = CardsPage.builder()
                .cards(from(cardsRepository.findAllByUser_Id(currentUserId)))
                .build();

        return ProfileDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .isHelper(user.getIsHelper())
                .cards(userCards)
                .build();
    }

    @Override
    public CardDto editCardStatus(Long currentUserId, Long cardId, Boolean cardStatus) {
        User user = usersRepository.findById(currentUserId)
                .orElseThrow(IllegalArgumentException::new);
        Card card = cardsRepository.findById(cardId).orElseThrow(
                () -> new NotFoundException("Category <" + cardId + "> not found"));
        if (!user.getCards().contains(card)) {
            throw new ForbiddenException("карточка не принадлежит пользователю");
        }
        card.setIsActive(cardStatus);
        cardsRepository.save(card);
        return CardDto.from(card);
    }
}
