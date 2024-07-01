create table hotel_feedback (
    hotel_id bigint references hotels(id),
    feedback_id bigint references feedback(id),
    primary key (hotel_id, feedback_id)
);