# TTL + DLX

```text
mensaje → queue con TTL → expira → DLX → DLQ
```

Un mensaje puede llegar a DLQ sin que ningún consumer lo rechace.
