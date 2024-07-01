package org.senla.komar.spring.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.senla.komar.spring.dto.GuestDto;
import org.senla.komar.spring.entity.Guest;
import org.senla.komar.spring.exception.GuestNotFoundException;
import org.senla.komar.spring.mapper.GuestMapper;
import org.senla.komar.spring.mapper.GuestMapperImpl;
import org.senla.komar.spring.repository.GuestDao;
import org.senla.komar.spring.service.GuestService;
import org.senla.komar.spring.util.MockTestData;

@RunWith(MockitoJUnitRunner.class)
public class GuestServiceImplTest {

    @Mock
    private GuestDao guestDaoMock;
    @Spy
    private GuestMapper guestMapperSpy = new GuestMapperImpl();
    private GuestServiceImpl guestService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        guestService = new GuestServiceImpl(guestDaoMock, guestMapperSpy);
    }

    @Test
    public void createGuest() {
        GuestDto guestDto = MockTestData.buildCreateGuestDto();
        Guest guest = guestMapperSpy.toGuest(guestDto);
        Mockito.doNothing().when(guestDaoMock).create(guest);
        guestService.createGuest(guestDto);
        Mockito.verify(guestDaoMock).create(guest);
    }

    @Test
    public void getGuestById() {
        GuestDto guestDto = MockTestData.existGuestDto();
        Guest guest = guestMapperSpy.toGuest(guestDto);
        Mockito.when(guestDaoMock.readById(guest.getId())).thenReturn(guest);
        GuestDto actualGuestDto = guestService.getGuestById(guest.getId());
        Assert.assertEquals(guestDto, actualGuestDto);
    }

    @Test
    public void deleteById() {
        Guest guest = MockTestData.existGuest();
        Mockito.doNothing().when(guestDaoMock).deleteById(guest.getId());
        guestService.deleteById(guest.getId());
        Mockito.verify(guestDaoMock).deleteById(guest.getId());
    }

    @Test
    public void updateGuestById() {
        GuestDto guestDto = MockTestData.buildUpdateGuestDto();
        Guest guest = guestMapperSpy.toGuest(guestDto);
        Mockito.when(guestDaoMock.update(guest.getId(), guest)).thenReturn(guest);
        guestService.updateGuestById(guestDto.getId(), guestDto);
        guestDto.setId(guest.getId());
        Mockito.verify(guestDaoMock).update(guest.getId(), guest);
    }

    @Test
    public void getGuestByPhoneNumber() {
        GuestDto guestDto = MockTestData.existGuestDto();
        Guest guest = guestMapperSpy.toGuest(guestDto);
        Mockito.when(guestDaoMock.getGuestByPhoneNumber(guest.getPerson().getPhoneNumber()))
                .thenReturn(guest);
        GuestDto actualGuestDto = guestService
                .getGuestByPhoneNumber(guestDto.getPerson().getPhoneNumber());
        Mockito.verify(guestDaoMock).getGuestByPhoneNumber(guestDto.getPerson().getPhoneNumber());
        Assert.assertEquals(guestDto, actualGuestDto);
    }

    @Test(expected = GuestNotFoundException.class)
    public void notFoundTest() {
        Long id = MockTestData.existGuest().getId();
        Mockito.when(guestDaoMock.readById(id)).thenReturn(null);
        guestService.getGuestById(id);
        Mockito.verify(guestDaoMock).readById(id);
    }

}