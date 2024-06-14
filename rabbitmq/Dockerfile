FROM rabbitmq:3.12.11-management

RUN apt-get update
RUN apt-get install -y curl wget less unzip vim

ADD https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases/download/v3.12.0/rabbitmq_delayed_message_exchange-3.12.0.ez $RABBITMQ_HOME/plugins/
RUN chown rabbitmq:rabbitmq $RABBITMQ_HOME/plugins/*

RUN rabbitmq-plugins enable --offline rabbitmq_delayed_message_exchange
RUN rabbitmq-plugins enable --offline rabbitmq_consistent_hash_exchange
RUN rabbitmq-plugins enable --offline rabbitmq_management
RUN rabbitmq-plugins enable --offline rabbitmq_shovel rabbitmq_shovel_management

COPY ./definitions.json /etc/rabbitmq/definitions.json
ENV RABBITMQ_SERVER_ADDITIONAL_ERL_ARGS '-rabbitmq_management load_definitions "/etc/rabbitmq/definitions.json"'

EXPOSE 15671 15672