BEGIN TRANSACTION;
PRAGMA foreign_keys=ON;
PRAGMA encoding = "UTF-8";
CREATE TABLE `users` (
    `id` INTEGER PRIMARY KEY,
    `mail_address` TEXT NOT NULL UNIQUE,
    `member_type` TEXT NOT NULL,
    `password` TEXT NOT NULL,
    `created_at` TEXT NOT NULL DEFAULT (DATETIME('now', '+9 hours')),
    `updated_at` TEXT NOT NULL DEFAULT (DATETIME('now', '+9 hours')),
    CHECK(member_type = 'passenger' or member_type = 'crew')
);
CREATE TABLE `orders` (
    `id` INTEGER PRIMARY KEY,
    `passenger_id` INTEGER NOT NULL,
    `crew_id` INTEGER,
    `dispatch_point` TEXT NOT NULL,
    `order_status` TEXT NOT NULL DEFAULT 'requested',
    `ordered_at` TEXT NOT NULL DEFAULT (DATETIME('now', '+9 hours')),
    `estimated_dispatched_at` TEXT,
    `dispatched_at` TEXT,
    `completed_at` TEXT,
    `created_at` TEXT NOT NULL DEFAULT (DATETIME('now', '+9 hours')),
    `updated_at` TEXT NOT NULL DEFAULT (DATETIME('now', '+9 hours')),
    FOREIGN KEY (passenger_id) REFERENCES users(id),
    CHECK(order_status = 'requested' or order_status = 'accepted'  or order_status = 'dispatched' or order_status = 'completed')
);
CREATE TRIGGER trigger_users_updated_at AFTER UPDATE ON users
BEGIN
    UPDATE users SET updated_at = DATETIME('now', '+9 hours') WHERE id == NEW.id;
END;
CREATE TRIGGER trigger_orders_updated_at AFTER UPDATE ON orders
BEGIN
    UPDATE orders SET updated_at = DATETIME('now', '+9 hours') WHERE id == NEW.id;
END;
COMMIT TRANSACTION;
