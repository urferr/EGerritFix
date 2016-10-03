FROM java:openjdk-7-jre

MAINTAINER EGerrit <egerrit-dev@eclipse.org>

ENV GERRIT_USER gerrit2
ENV GERRIT_HOME /home/$GERRIT_USER
ENV GERRIT_WAR $GERRIT_HOME/gerrit.war
ARG GERRIT_VERSION=2.13.1

RUN useradd -m $GERRIT_USER

RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get install --no-install-recommends -y \
		git-core \
		openssh-client \
		vim \
    && rm -rf /var/lib/apt/lists/*

#Download gerrit and put it in place
ADD https://gerrit-releases.storage.googleapis.com/gerrit-$GERRIT_VERSION.war $GERRIT_WAR

#Copy files necessary to do the setup
COPY setupServer.sh $GERRIT_HOME/
RUN chmod +x $GERRIT_HOME/setupServer.sh
RUN chown -R $GERRIT_USER:$GERRIT_USER $GERRIT_HOME

USER $GERRIT_USER
WORKDIR $GERRIT_HOME

RUN $GERRIT_HOME/setupServer.sh

EXPOSE 8080 29418

CMD ["/home/gerrit2/gerrit/bin/gerrit.sh", "daemon"]
