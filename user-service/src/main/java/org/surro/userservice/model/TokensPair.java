package org.surro.userservice.model;

public record TokensPair(String requestToken, String accessToken, int tokenDuration) {
}
