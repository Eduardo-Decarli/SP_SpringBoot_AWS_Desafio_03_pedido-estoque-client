ALTER TABLE product DROP CONSTRAINT IF EXISTS fk_product_order_id;
ALTER TABLE orders DROP CONSTRAINT IF EXISTS fk_orders_email_clients;
