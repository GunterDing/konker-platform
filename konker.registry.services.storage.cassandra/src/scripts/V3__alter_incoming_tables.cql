ALTER TABLE incoming_events ADD geo_lat double;
ALTER TABLE incoming_events ADD	geo_lon double;
ALTER TABLE incoming_events ADD	geo_hdop bigint;
ALTER TABLE incoming_events ADD	geo_elev double;
ALTER TABLE incoming_events ADD ingested_timestamp bigint;

ALTER TABLE incoming_events_deleted ADD geo_lat double;
ALTER TABLE incoming_events_deleted ADD geo_lon double;
ALTER TABLE incoming_events_deleted ADD geo_hdop bigint;
ALTER TABLE incoming_events_deleted ADD geo_elev double;
ALTER TABLE incoming_events_deleted ADD ingested_timestamp bigint;

ALTER TABLE incoming_events_device_guid ADD geo_lat double;
ALTER TABLE incoming_events_device_guid ADD geo_lon double;
ALTER TABLE incoming_events_device_guid ADD geo_hdop bigint;
ALTER TABLE incoming_events_device_guid ADD geo_elev double;
ALTER TABLE incoming_events_device_guid ADD ingested_timestamp bigint;

ALTER TABLE incoming_events_device_guid_channel ADD geo_lat double;
ALTER TABLE incoming_events_device_guid_channel ADD geo_lon double;
ALTER TABLE incoming_events_device_guid_channel ADD geo_hdop bigint;
ALTER TABLE incoming_events_device_guid_channel ADD geo_elev double;
ALTER TABLE incoming_events_device_guid_channel ADD ingested_timestamp bigint;

ALTER TABLE incoming_events_channel ADD geo_lat double;
ALTER TABLE incoming_events_channel ADD geo_lon double;
ALTER TABLE incoming_events_channel ADD geo_hdop bigint;
ALTER TABLE incoming_events_channel ADD geo_elev double;
ALTER TABLE incoming_events_channel ADD ingested_timestamp bigint;
