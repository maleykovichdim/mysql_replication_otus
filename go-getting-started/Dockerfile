#docker build -t go-app . 
#docker run --name=go-web-app --network="host" -p 80:8080 go-app 
# --network="host"

FROM golang:latest
WORKDIR /go-getting-started

COPY go.mod ./
COPY go.sum ./
RUN go mod download
COPY ./ ./

RUN go mod download
RUN go build -o main .
CMD ["./main"]