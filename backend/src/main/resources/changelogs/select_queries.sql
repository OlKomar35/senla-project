--Все адреса
SET enable_seqscan TO off

select * from addresses a
inner join cities c on a.city_id = c.id_city
inner join streets s on a.street_id  = s.id_street;

--Адреса всех достопримечательностей
select * from attractions a
inner join addresses a2 on a.address_id = a2.id_address;

--Вся информация о гостиницах
select * from hotels h
inner join addresses a on h.address_id = a.id_address
inner join types_hotel th on h.type_hotel_id = th.id_type;

--Информация об всех услугах по всем гостиницам
select h.hotel_name , ah.amenity_name  from hotels h
inner join hotel_amenities ha on ha.hotel_id = h.id_hotel
inner join amenities_h ah on ah.id_amenity = ha.amenity_id ;

--Все отзывы о гостинице
select h.hotel_name, f.* from hotels h
left join hotel_feedback hf  on h.id_hotel = hf.hotel_id
inner join feedback f on f.id_feedback = hf.feedback_id;

--Все сотрудники отеля
select h.hotel_name, p.*, e.job_title_id ,e.status_employee  from hotels h
left join hotel_employees he  on h.id_hotel = he.hotel_id
inner join employees e  on e.id_employee = he.employee_id
inner join people p on p.id_person  = e.person_id;

--Вся инфармация о номерах в отелях
select h.hotel_name , r.room_number , r.floor_room ,
	   r.type_room_id , r.availability , r.max_occupancy from hotels h
left join rooms r on h.id_hotel = r.hotel_id;

--Вся инф-а о номерах
select * from rooms r
inner join types_room tr on tr.id_type= r.type_room_id
inner join room_amenities ra on ra.room_id = r.id_room
inner join amenities_r ar on ar.id_amenity = ra.amenity_id;

--Информация о бронировании
select * from bookings b
inner join booking_statuses bs on b.booking_status_id = bs.id_status
inner join types_payment tp on tp.id_type = b.type_payment_id
inner join payment_statuses ps on ps.id_payment_status = b.payment_status_id
inner join guests g on b.guest_id = g.id_guest
inner join rooms r on r.id_room = b.room_id;

