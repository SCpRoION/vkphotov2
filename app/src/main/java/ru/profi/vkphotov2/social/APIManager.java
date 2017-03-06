package ru.profi.vkphotov2.social;

/**
 * Created by Kamo Spertsyan on 23.02.2017.
 */
public class APIManager {

    private static SocialNetworkAPI current = new VkApiImpl(); // Используемое API

    /**
     * Получить используемое API социальной сети
     * @return используемое API социальной сети
     */
    public static SocialNetworkAPI getAPIImpl() {
        return current;
    }
}
