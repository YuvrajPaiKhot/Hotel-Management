CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    mobile VARCHAR(20),
    address VARCHAR(255)
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    roles VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE rooms (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    room_number VARCHAR(255) UNIQUE NOT NULL,
    type VARCHAR(255),
    price DOUBLE,
    is_available BOOLEAN,
    max_occupancy INT,
    description VARCHAR(500),
    amenities VARCHAR(255)
);

CREATE TABLE bookings (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    check_in_date DATE,
    check_out_date DATE,
    num_adults INT,
    num_children INT,
    status VARCHAR(50),
    total_amount DOUBLE,
    booking_date TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (room_id) REFERENCES rooms(id)
);

CREATE TABLE bills (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    issue_date TIMESTAMP,
    total_amount DOUBLE,
    payment_status VARCHAR(50),
    details CLOB,
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

CREATE TABLE complaints (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255),
    description VARCHAR(1000),
    category VARCHAR(255),
    status VARCHAR(50),
    submission_date TIMESTAMP,
    resolution_notes VARCHAR(1000),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
