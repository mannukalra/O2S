package handlers

import (
	"fmt"

	"github.com/pelletier/go-toml/v2"
)

type Entry struct {
	Key   string
	Value interface{}
}

type Plugin struct {
	Name    string
	Entries []Entry
}
type Conf struct {
	Plugins []Plugin
}

type TFConfig struct {
	plugins map[string]map[string]interface{}
}

type TFPlugin map[string]interface{}

func GenerateConf1() {

	conf := TFConfig{}
	conf.plugins = make(map[string]map[string]interface{})

	conf.plugins["global"] = map[string]interface{}{"dn": 22, "status": "live"}
	conf.plugins["agent"] = map[string]interface{}{"dne": 222, "statuses": "lively"}

	conf.plugins["inputs"] = map[string]interface{}{"dna1": 221, "status1": "live1"}
	conf.plugins["outputs"] = map[string]interface{}{"dnea1": 2221, "statuses1": "lively1"}

	fmt.Printf("%+v\n", conf)
	tomlData, err := toml.Marshal(&conf)

	if err != nil {
		fmt.Printf("Error while Marshaling. %v", err)
	}
	fmt.Println("toml>>>> :" + string(tomlData))
}

func GenerateConf() {
	var tfConf = make(map[string]TFPlugin)

	tfConf["inputs"] = TFPlugin{"dn": 22, "status": "live"}
	tfConf["outputs"] = TFPlugin{"version": 2, "xyz": "abc"}

	fmt.Println(tfConf)
	tomlData, err := toml.Marshal(&tfConf)

	if err != nil {
		fmt.Printf("Error while Marshaling. %v", err)
	}
	fmt.Println(string(tomlData))
}
