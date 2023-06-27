package de.ait.gethelp.controllers;

import de.ait.gethelp.controllers.api.CardsApi;
import de.ait.gethelp.dto.CardDto;
import de.ait.gethelp.dto.CardsPage;
import de.ait.gethelp.dto.NewCardDto;
import de.ait.gethelp.security.details.AuthenticatedUser;
import de.ait.gethelp.services.CardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CardsController implements CardsApi {

    private final CardsService cardsService;

    @Override
    public ResponseEntity<CardsPage> getAll() {
        return ResponseEntity
                .ok(cardsService.getAll());
    }

    @Override
    public ResponseEntity<CardDto> getById(Long cardId) {
        return ResponseEntity.ok(cardsService.getById(cardId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<CardDto> addCard(AuthenticatedUser authenticatedUser, NewCardDto newCard) {
        Long currentUserId = authenticatedUser.getUser().getId();
        return ResponseEntity.status(201)
                .body(cardsService.addCard(currentUserId, newCard));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<CardDto> editCard(AuthenticatedUser authenticatedUser, Long cardId, NewCardDto editedCard) {
        Long currentUserId = authenticatedUser.getUser().getId();
        return ResponseEntity.ok(cardsService.editCard(currentUserId, cardId, editedCard));
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @Override
    public ResponseEntity<CardDto> deleteCard(Long cardId) {
        return ResponseEntity.status(204)
                .body(cardsService.deleteCard(cardId));
    }
}
