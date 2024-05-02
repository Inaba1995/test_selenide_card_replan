package ru.netology.test;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegistrationByCardInfo {

    private final String name;
    private  final String phone;
    private final String city;
    private final String date;
    private final String dateNext;

}

