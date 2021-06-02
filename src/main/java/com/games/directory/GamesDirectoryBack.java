package com.games.directory;

import com.games.directory.model.GameDao;
import com.games.directory.repository.GameRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GamesDirectoryBack {
	private static ApplicationContext applicationContext;
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(GamesDirectoryBack.class, args);
		GameRepository gameRepository = (GameRepository) applicationContext.getBean(GameRepository.class);
		gameRepository.save(new GameDao("Halo","Xbox","Action","https://www.enter.co/wp-content/uploads/2019/06/Halo-1024x768.jpg"));
		gameRepository.save(new GameDao("God of war","PlayStation","Action","https://elcanciller.com/wp-content/uploads/2019/09/1524590603-gow-og-image.jpg"));
		gameRepository.save(new GameDao("Assassins Creed","PC","Action","https://images3.alphacoders.com/823/thumb-1920-82365.jpg"));
		gameRepository.save(new GameDao("Albion online es un mmorpg no lineal","PC","Aventure","https://imagekit.androidphoria.com/wp-content/uploads/Descargar-el-APK-de-Albion-Online-Android.jpg"));
		gameRepository.save(new GameDao("Call of Duty Mobile","Mobile","Action","https://cdn1.dotesports.com/wp-content/uploads/sites/4/2021/02/10151019/GarenaWorld.png"));

	}
}
