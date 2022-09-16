package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"regexp"
	"strings"
)

func main() {

	inputs := make(map[string]string)
	content, err := ioutil.ReadFile("../../static/telegraf.conf")

	if err != nil {
		log.Fatal(err)
	}

	str1 := string(content)
	re := regexp.MustCompile(`([\w\s-,'/&().]+)([\r\n #]+)(\[\[inputs.)([\w]+)(]])`)

	fmt.Printf("Pattern: %v\n", re.String()) // print pattern

	submatchall := re.FindAllStringSubmatch(str1, -1)
	for _, element := range submatchall {
		inputs[element[4]] = strings.TrimSpace(element[1])
	}

	fmt.Println(len(inputs))

	fmt.Println(inputs["elasticsearch"])

	// keys := reflect.ValueOf(inputs).MapKeys()
	// fmt.Println(keys)
}
