INSERT INTO bookings(created_at, updated_at, guest_id, room_id, booking_status, check_in_date, check_out_date,
                     arrival_time, cost_booking,
                     payment_status, comment_booking, type_payment, type_food)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 1, 'NEW', '2024-05-15', '2024-05-17', '2024-05-15 14:00:00',
        105.20, 'PROCESSING', null, 'CASHLESS', 'BB');
INSERT INTO bookings(created_at, updated_at, guest_id, room_id, booking_status, check_in_date, check_out_date,
                     arrival_time, cost_booking,
                     payment_status, comment_booking, type_payment, type_food)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 1, 'CANCELLED', '2024-05-18', '2024-05-22', '2024-05-18 12:00', 1020,
        'PROCESSING', null, 'CASH', 'HB');






