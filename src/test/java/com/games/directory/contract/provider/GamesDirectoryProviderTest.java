package com.games.directory.contract.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.games.directory.controller.GameController;
import com.games.directory.domain.Game;
import com.games.directory.service.interfaces.GameService;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;

@PactBroker(url = "${PACT_BROKER_BASE_URL}",
authentication = @PactBrokerAuth(token = "${PACT_BROKER_TOKEN}"))
@Provider("GamesDirectoryBack")
@ExtendWith(MockitoExtension.class)
public class GamesDirectoryProviderTest {
	
	@Mock
	private GameService gameService;
	
	@InjectMocks
	private GameController gameController;
	
	@TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }
	
	@BeforeEach
	public void changeContext(PactVerificationContext context) {
        MockMvcTestTarget testTarget = new MockMvcTestTarget();
        testTarget.setControllers(gameController);
        context.setTarget(testTarget);
    }
	
	@State("add a game")
	public void addGame() {
		Game game = new Game();
		game.setName("Grand Theft Auto: San Andreas");
		game.setConsole("PC");
		game.setGenre("Action");
		game.setImg("www.vicecity.com/img");
		
		Mockito.when(gameService.create(Mockito.any(Game.class))).thenReturn(game);
	}
	
	@State("list games")
	public void listGames() {
		Game game = new Game();
		game.setName("Dragon Ball Z: Budokai Tenkaichi 3");
		game.setConsole("PlayStation");
		game.setGenre("Fight");
		game.setImg("www.dbzbt3.com/img");
		
		ArrayList<Game> games = new ArrayList<Game>();
		games.add(game);
		
		Mockito.when(gameService.getAll()).thenReturn(games);
	}
	
	@State("delete a game")
	public void deleteMovie() {
		Long id = 1L;
		Mockito.doAnswer((i) -> {
			assertEquals(id, i.getArgument(0));
			return null;
		}).when(gameService).delete(id);
	}
	
	@State("edit a game")
	public void editGame() {
		Game game = new Game();
		game.setId(1);
		game.setName("Beyond Two Souls");
		game.setConsole("Xbox");
		game.setGenre("Interactive");
		game.setImg("www.beyondtwosouls.com/img");
		
		Mockito.when(gameService.update(Mockito.any(Game.class))).thenReturn(game);
	}
}
