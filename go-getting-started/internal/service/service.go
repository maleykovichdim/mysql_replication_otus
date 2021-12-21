package service

import (
	"database/sql"
	"net/url"

	"github.com/hako/branca"
)

// Service contains the core business logic separated from the transport layer.
// You can use it to back a REST, gRPC or GraphQL API.
type Service struct {
	db      *sql.DB
	db_read *sql.DB
	origin  url.URL
	codec   *branca.Branca
}

// New service implementation.
func New(db *sql.DB, db_read *sql.DB,
	origin url.URL, tokenKey string) *Service {
	cdc := branca.NewBranca(tokenKey)
	cdc.SetTTL(uint32(tokenLifespan.Seconds()))

	return &Service{
		db:      db,
		db_read: db_read,
		origin:  origin,
		codec:   cdc,
	}
}
