package org.senla.komar.spring.util;

import lombok.experimental.UtilityClass;
import org.senla.komar.spring.dto.*;
import org.senla.komar.spring.entity.*;
import org.senla.komar.spring.enums.TypeHotel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MockTestData {

  public static final String ADMIN_USERNAME = "admin";
  public static final String ADMIN_PASSWORD = "admin";
  public static final String ENCODING_ADMIN_PASSWORD = "$2a$12$CFBlQTIZLfmUAM5vIOLGTuK0I3zlk3r.YzTZXGNuUnzNGuQYDEnbe";
  public static final String ADMIN_ROLE = "ADMIN";
  public static final String USERNAME = "user";
  public static final String USER_PASSWORD = "root";
  public static final String USER_ROLE = "USER";

  //region City data
  public City buildCreateCity() {
    return City.builder()
        .name("Скидель")
        .build();
  }

  public CityDto buildCreateCityDto() {
    return CityDto.builder()
        .name("Скидель")
        .build();
  }

  public City existCity() {
    return City.builder().id(7L).name("Гродно").build();
  }

  public CityDto existCityDto() {
    return CityDto.builder().id(7L).name("Гродно").build();
  }

  public List<CityDto> existCitiesDto() {
    List<CityDto> cities = new ArrayList<>();
    cities.add(existCityDto());
    cities.add(CityDto.builder().id(1L).name("Минск").build());
    return cities;
  }

  public City buildUpdateCity() {
    return City.builder()
        .id(1L)
        .name("Мосты")
        .build();
  }

  public CityDto buildUpdateCityDto() {
    return CityDto.builder()
        .id(1L)
        .name("Мосты")
        .build();
  }
  //endregion

  //region Street data
  public Street buildCreateStreet() {
    return Street.builder()
        .name("Летняя")
        .build();
  }

  public StreetDto buildCreateStreetDto() {
    return StreetDto.builder()
        .name("Летняя")
        .build();
  }

  public Street existStreet() {
    return Street.builder().id(7L).name("Горького").build();
  }

  public StreetDto existStreetDto() {
    return StreetDto.builder().id(7L).name("Горького").build();
  }

  public Street buildUpdateStreet() {
    return Street.builder()
        .id(7L)
        .name("Тызенгауза")
        .build();
  }

  public StreetDto buildUpdateStreetDto() {
    return StreetDto.builder()
        .id(7L)
        .name("Тызенгауза")
        .build();
  }

  //endregion

  //region Address data
  public Address buildCreateAddress() {
    return Address.builder()
        .city(buildCreateCity())
        .street(buildCreateStreet())
        .houseNumber(147)
        .build();
  }

  public Address existAddress() {
    return Address.builder()
        .id(2L)
        .city(existCity())
        .street(existStreet())
        .houseNumber(98)
        .build();
  }

  public Address buildUpdateAddress() {
    Address address = existAddress();
    address.setHouseNumber(285);
    return address;
  }

  public AddressDto buildCreateAddressDto() {
    return AddressDto.builder()
        .city(buildCreateCityDto())
        .street(buildCreateStreetDto())
        .houseNumber(147)
        .build();
  }

  public AddressDto existAddressDto() {
    return AddressDto.builder()
        .id(2L)
        .city(existCityDto())
        .street(existStreetDto())
        .houseNumber(98)
        .build();
  }

  public AddressDto buildUpdateAddressDto() {
    AddressDto addressDto = existAddressDto();
    addressDto.setHouseNumber(285);
    return addressDto;
  }
  //endregion

  //region Person data
  public Person buildCreatePerson() {
    return Person.builder()
        .surname("Шиманская")
        .firstname("Ольга")
        .middleName("Васильевна")
        .passportSeries("КН")
        .passportNumber(2587413)
        .phoneNumber("+375331234576")
        .email("polkjjndhbsfed@gmail.com")
        .login(USERNAME)
        .password(ENCODING_ADMIN_PASSWORD)
        .build();
  }


  public Person existPerson() {
    return Person.builder()
        .id(3L)
        .surname("Сущинский")
        .firstname("Иван")
        .middleName("Владимирович")
        .passportSeries("КН")
        .passportNumber(2587419)
        .phoneNumber("+375331524778")
        .email("ivan_s125@gmail.com")
        .build();
  }

  public Person buildUpdatePerson() {
    Person person = existPerson();
    person.setFirstname("Павел");
    person.setPassportNumber(1234567);
    return person;
  }

  public PersonDto buildCreatePersonDto() {
    return PersonDto.builder()
        .surname("Шиманская")
        .firstname("Ольга")
        .middleName("Васильевна")
        .passportSeries("КН")
        .passportNumber(2587413)
        .phoneNumber("+375331234576")
        .email("polkjjndhbsfed@gmail.com")
        .login(USERNAME)
        .password(ENCODING_ADMIN_PASSWORD)
        .build();
  }


  public PersonDto existPersonDto() {
    return PersonDto.builder()
        .id(3L)
        .surname("Сущинский")
        .firstname("Иван")
        .middleName("Владимирович")
        .passportSeries("КН")
        .passportNumber(2587419)
        .phoneNumber("+375331524778")
        .email("ivan_s125@gmail.com")
        .build();
  }

  public PersonDto buildUpdatePersonDto() {
    PersonDto personDto = existPersonDto();
    personDto.setFirstname("Павел");
    personDto.setPassportNumber(1234567);
    return personDto;
  }
  //endregion

  //region Guest data
  public Guest buildCreateGuest() {
    return Guest.builder()
        .person(buildCreatePerson())
        .rank(BigDecimal.valueOf(8, 1))
        .build();
  }

  public Guest existGuest() {
    return Guest.builder()
        .id(3L)
        .person(existPerson())
        .rank(BigDecimal.valueOf(0, 1))
        .build();
  }

  public Guest buildUpdateGuest() {
    Guest guest = existGuest();
    Person person = guest.getPerson();
    person.setEmail("hggfdfds@mail.ru");
    return guest;
  }

  public GuestDto buildCreateGuestDto() {
    return GuestDto.builder()
        .person(buildCreatePersonDto())
        .build();
  }

  public GuestDto existGuestDto() {
    return GuestDto.builder()
        .id(3L)
        .person(existPersonDto())
        .build();
  }

  public GuestDto buildUpdateGuestDto() {
    GuestDto guestDto = existGuestDto();
    PersonDto personDto = guestDto.getPerson();
    personDto.setEmail("hggfdfds@mail.ru");
    return guestDto;
  }
  //endregion

  //region Hotel data
  public Hotel buildCreateHotel() {
    return Hotel.builder()
        .name("Спутник")
        .address(buildCreateAddress())
        .phoneNumber("+375295671232")
        .email("qwe_diyuygrt@mail.ru")
        .webSite("ljfiyfuysd.by")
        .countRooms(15)
        .typeHotel(TypeHotel.MOTEL)
        .openingDate(LocalDate.of(2015, 12, 12))
        .build();
  }

  public Hotel existHotel() {
    return Hotel.builder()
        .id(3L)
        .name("Зоря")
        .address(existAddress())
        .phoneNumber("+375292345678")
        .email("qwertjkhcujhv@mail.ru")
        .webSite("sfedqwerty.by")
        .countRooms(215)
        .typeHotel(TypeHotel.THREE_STARS)
        .openingDate(LocalDate.of(2015, 12, 12))
        .build();
  }

  public Hotel buildUpdateHotel() {
    Hotel hotel = existHotel();
    hotel.setTypeHotel(TypeHotel.TWO_STARS);
    hotel.getAddress().getCity().setName("Гродно");
    return hotel;
  }

  public List<Hotel> hotelsList() {
    List<Hotel> hotels = new ArrayList<>();
    hotels.add(existHotel());
    hotels.add(buildUpdateHotel());
    return hotels;
  }

  public HotelDtoShortInfo buildCreateHotelShortDto() {
    return HotelDtoShortInfo.builder()
        .name("Спутник")
        .address(buildCreateAddressDto())
        .build();
  }

  public HotelDtoShortInfo existHotelShortDto() {
    return HotelDtoShortInfo.builder()
        .id(3L)
        .name("Зоря")
        .address(existAddressDto())
        .build();
  }

  public HotelDtoShortInfo buildUpdateHotelShortDto() {
    HotelDtoShortInfo hotelDtoShortInfo = existHotelShortDto();
    hotelDtoShortInfo.getAddress().getCity().setName("Гродно");
    return hotelDtoShortInfo;
  }

  public HotelDtoFullInfo buildCreateHotelFullDto() {
    return HotelDtoFullInfo.builder()
        .name("Спутник")
        .address(buildCreateAddressDto())
        .phoneNumber("+375295671232")
        .email("qwe_diyuygrt@mail.ru")
        .webSite("ljfiyfuysd.by")
        .rank(BigDecimal.valueOf(7, 1))
        .typeHotel(TypeHotel.MOTEL)
        .build();
  }

  public HotelDtoFullInfo existHotelFullDto() {
    return HotelDtoFullInfo.builder()
        .id(3L)
        .name("Зоря")
        .address(existAddressDto())
        .phoneNumber("+375292345678")
        .email("qwertjkhcujhv@mail.ru")
        .webSite("sfedqwerty.by")
        .rank(BigDecimal.valueOf(0, 1))
        .typeHotel(TypeHotel.THREE_STARS)
        .build();
  }

  public HotelDtoFullInfo buildUpdateHotelFullDto() {
    HotelDtoFullInfo hotelDtoFullInfo = existHotelFullDto();
    hotelDtoFullInfo.setTypeHotel(TypeHotel.TWO_STARS);
    hotelDtoFullInfo.getAddress().getCity().setName("Гродно");
    return hotelDtoFullInfo;
  }
  //endregion
}
