create table guest_feedback (
    guest_id bigint references guests(id),
    feedback_id bigint references feedback(id),
    primary key (guest_id, feedback_id)
);
