CREATE FUNCTION reservation_status_from_text(text) RETURNS reservation_status AS $$
SELECT $1::reservation_status;
$$ LANGUAGE sql;