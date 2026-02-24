INSERT INTO shows (id, theatre_id, screen_id, movie_title, show_time)
VALUES (101, 1, 10, 'Interstellar', TIMESTAMP '2026-02-10 18:30:00');


INSERT INTO show_seats(show_id, seat_id, status) VALUES (101, 501, 'AVAILABLE');
INSERT INTO show_seats(show_id, seat_id, status) VALUES (101, 502, 'AVAILABLE');
INSERT INTO show_seats(show_id, seat_id, status) VALUES (101, 503, 'AVAILABLE');
INSERT INTO show_seats(show_id, seat_id, status) VALUES (101, 504, 'AVAILABLE');
