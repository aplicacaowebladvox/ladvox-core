ALTER TABLE users_notifications
    ADD dt_user_notification_deleted_date TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE request_appointments
    ALTER COLUMN id_user_of_conclusion DROP NOT NULL;

ALTER TABLE request_appointments
    ALTER COLUMN id_user_of_conclusion DROP DEFAULT;

INSERT INTO public.permissions (id_permission, cd_permission, nm_permission) VALUES ('e07b08c4-4fd3-42cb-aa82-9de0a5398670', 'solicitacoes-atendimento.conclude', 'Menu "Solicitações" > Listagem > Ação "Concluir" & Menu "Solicitações" > Ação "Visualizar" > Botão "Concluir"');
