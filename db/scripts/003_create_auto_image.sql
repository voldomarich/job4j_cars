CREATE TABLE IF NOT EXISTS auto_image (
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    path  VARCHAR(255) NOT NULL,
    post_id int references auto_post(id)
);
