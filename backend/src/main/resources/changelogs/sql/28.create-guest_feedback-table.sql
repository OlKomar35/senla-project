create or replace function update_hotel_rank()
    returns trigger as '
begin
    update hotels
    set rank =(
        select avg(f.score)
        from feedback as f
        join hotel_feedback as hf on f.id = hf.feedback_id
        where new.hotel_id = hf.hotel_id
    )
    where id = new.hotel_id;

    return new;
end;
' language plpgsql;

create trigger update_hotel_rank_trigger
    after insert on hotel_feedback
    for each row
execute procedure update_hotel_rank();

