package de.ait.gethelp.services;

import de.ait.gethelp.dto.CardDto;
import de.ait.gethelp.dto.CardsPage;
import de.ait.gethelp.dto.NewCardDto;

public interface CardsService {

    public CardsPage getAll();

    public CardDto getById(Long cardId);

    public CardDto addCard(Long currentUserId, NewCardDto newCard);

    public CardDto editCard(Long currentUserId, Long cardId, NewCardDto editedCard);

    public CardDto deleteCard(Long cardId);
}
