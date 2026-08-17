CREATE SEQUENCE IF NOT EXISTS medical_appointment_plannings_id_medical_appointment_plann_seq2 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS therapeutic_plan_attachments_id_therapeutic_plan_attachmen_seq1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS therapeutic_plan_comments_id_therapeutic_plan_comment_seq1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS therapeutic_plan_editor_histo_id_therapeutic_plan_editor_h_seq1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS therapeutic_plan_editors_hist_id_therapeutic_plan_editor_h_seq1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS users_notifications_id_user_notification_seq1 INCREMENT BY 1;

ALTER TABLE medical_appointments
DROP
COLUMN tx_medical_appointment_observation;

ALTER TABLE addresses
    ALTER COLUMN tx_address_zip_code DROP NOT NULL;
