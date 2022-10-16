package handlers

import (
	"fmt"

	"gopkg.in/yaml.v2"
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

func Main2() {
	plugin1 := Plugin{"gloabal", []Entry{Entry{"key1", "val1"}, Entry{"key2", 12}}}
	plugin2 := Plugin{"inputs", []Entry{Entry{"key11", "val11"}, Entry{"key21", 121}}}
	conf := []Plugin{plugin1, plugin2}
	fmt.Println(conf)

	fmt.Println(conf)
	yamlData, err := yaml.Marshal(&conf)

	if err != nil {
		fmt.Printf("Error while Marshaling. %v", err)
	}
	fmt.Printf(string(yamlData))
}

type TFConfig struct {
	plugins map[string]map[string]interface{}
}

type TFPlugin map[string]interface{}

func main1() {

	conf := TFConfig{}
	conf.plugins = make(map[string]map[string]interface{})

	conf.plugins["global"] = map[string]interface{}{"dn": 22, "status": "live"}
	conf.plugins["agent"] = map[string]interface{}{"dne": 222, "statuses": "lively"}

	conf.plugins["inputs"] = map[string]interface{}{"dna1": 221, "status1": "live1"}
	conf.plugins["outputs"] = map[string]interface{}{"dnea1": 2221, "statuses1": "lively1"}

	fmt.Println(conf)
	yamlData, err := yaml.Marshal(&conf)

	if err != nil {
		fmt.Printf("Error while Marshaling. %v", err)
	}
	fmt.Printf(string(yamlData))
}

func main3() {
	var tfConf = make(map[string]TFPlugin)

	tfConf["inputs"] = TFPlugin{"dn": 22, "status": "live"}
	tfConf["outputs"] = TFPlugin{"version": 2, "xyz": "abc"}

	fmt.Println(tfConf)
	// var decoderr = yaml.NewDecode()
	// decoderr yaml.DoubleQuotedStyle .DoubleQuotedStyle Encoder
	yamlData, err := yaml.Marshal(&tfConf)

	if err != nil {
		fmt.Printf("Error while Marshaling. %v", err)
	}
	fmt.Printf(string(yamlData))
}

// func (d map) MarshalYAML() ([]byte, error) {
// 	keys := make([]int, len(d))
// 	if keys[0] == "inputs" {
// 		return yaml.Marshal("[[[" + d + "]]]")
// 	}
// 	return yaml.Marshal(d)
// }