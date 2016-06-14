FROM alpine
RUN apk update
RUN apk add ruby
ADD killme.rb /
ENTRYPOINT ruby killme.rb
