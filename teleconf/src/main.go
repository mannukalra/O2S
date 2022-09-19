package main

import (
	"fmt"
	server "teleconf/handlers"
	tgplugins "teleconf/util"
)

func main() {

	fmt.Println(tgplugins.InputPlugins())

	server.StartServer()
}
