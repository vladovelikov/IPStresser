package com.ipstresser.app.domain.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CryptocurrencyViewModel {

    private String id;

    private String title;

    private String description;

    private String imageUrl;

}
