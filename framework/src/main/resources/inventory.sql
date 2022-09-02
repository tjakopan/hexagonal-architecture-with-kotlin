CREATE TABLE location
(
  id        int NULL PRIMARY KEY,
  address   VARCHAR(255),
  city      VARCHAR(255),
  state     VARCHAR(255),
  zip_code  VARCHAR(255),
  country   VARCHAR(255),
  latitude  FLOAT,
  longitude FLOAT,
  PRIMARY KEY (id)
);

CREATE TABLE router
(
  id             UUID PRIMARY KEY NOT NULL,
  parent_core_id UUID             NULL,
  type           VARCHAR(255),
  vendor         VARCHAR(255),
  model          VARCHAR(255),
  ip_protocol    VARCHAR(255),
  ip_address     VARCHAR(255),
  location_id    int,
  FOREIGN KEY (location_id) REFERENCES location (id)
);

CREATE TABLE switch
(
  id          UUID PRIMARY KEY NOT NULL,
  router_id   UUID,
  type        VARCHAR(255),
  vendor      VARCHAR(255),
  model       VARCHAR(255),
  ip_protocol VARCHAR(255),
  ip_address  VARCHAR(255),
  location_id int,
  PRIMARY KEY (id),
  FOREIGN KEY (location_id) REFERENCES location (id),
  FOREIGN KEY (router_id) REFERENCES router (id)
);

CREATE TABLE network
(
  id          int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  switch_id   UUID,
  ip_protocol VARCHAR(255),
  ip_address  VARCHAR(255),
  name        VARCHAR(255),
  cidr        int,
  PRIMARY KEY (id),
  FOREIGN KEY (switch_id) REFERENCES switch (id)
);

INSERT INTO location(id, address, city, state, zip_code, country, latitude, longitude)
VALUES (1, 'Amos Ln', 'Tully', 'NY', '13159', 'United States', '42.797310', '-76.130750');

INSERT INTO location(id, address, city, state, zip_code, country, latitude, longitude)
VALUES (2, '104 N Wolcott St', 'Casper', 'WY', '82601', 'United States', '42.850840', '-106.324150');

INSERT INTO router(id, parent_core_id, type, vendor, model, ip_protocol, ip_address, location_id)
VALUES ('b832ef4f-f894-4194-8feb-a99c2cd4be0c', null, 'CORE', 'CISCO', 'XYZ0001', 'IPV4', '1.0.0.1', 1);

INSERT INTO router(id, parent_core_id, type, vendor, model, ip_protocol, ip_address, location_id)
VALUES ('b832ef4f-f894-4194-8feb-a99c2cd4be0a', 'b832ef4f-f894-4194-8feb-a99c2cd4be0c', 'EDGE', 'JUNIPER', 'XYZ0001',
        'IPV4', '5.0.0.5', 1);

INSERT INTO router(id, parent_core_id, type, vendor, model, ip_protocol, ip_address, location_id)
VALUES ('b832ef4f-f894-4194-8feb-a99c2cd4be0b', 'b832ef4f-f894-4194-8feb-a99c2cd4be0c', 'EDGE', 'JUNIPER', 'XYZ0001',
        'IPV4', '6.0.0.6', 1);

INSERT INTO router(id, parent_core_id, type, vendor, model, ip_protocol, ip_address, location_id)
VALUES ('b07f5187-2d82-4975-a14b-bdbad9a8ad46', 'b832ef4f-f894-4194-8feb-a99c2cd4be0c', 'EDGE', 'HP', 'XYZ0002', 'IPV4',
        '2.0.0.2', 2);

INSERT INTO switch(id, router_id, type, vendor, model, ip_protocol, ip_address, location_id)
VALUES ('922dbcd5-d071-41bd-920b-00f83eb4bb46', 'b07f5187-2d82-4975-a14b-bdbad9a8ad46', 'LAYER3', 'JUNIPER', 'XYZ0004',
        'IPV4', '9.0.0.9', 1);

INSERT INTO switch(id, router_id, type, vendor, model, ip_protocol, ip_address, location_id)
VALUES ('922dbcd5-d071-41bd-920b-00f83eb4bb47', 'b07f5187-2d82-4975-a14b-bdbad9a8ad46', 'LAYER3', 'CISCO', 'XYZ0002',
        'IPV4', '10.0.0.10', 1);

INSERT INTO network(switch_id, ip_protocol, ip_address, name, cidr)
VALUES ('922dbcd5-d071-41bd-920b-00f83eb4bb46', 'IPV4', '10.0.0.0', 'HR', 8);
INSERT INTO network(switch_id, ip_protocol, ip_address, name, cidr)
VALUES ('922dbcd5-d071-41bd-920b-00f83eb4bb46', 'IPV4', '20.0.0.0', 'Marketing', 8);
INSERT INTO network(switch_id, ip_protocol, ip_address, name, cidr)
VALUES ('922dbcd5-d071-41bd-920b-00f83eb4bb46', 'IPV4', '30.0.0.0', 'Engineering', 8);