package main

import (
	// "fmt"
	"fmt"
	"net/url"
	handlers "teleconf/handlers"
	tgplugins "teleconf/util"

	gapi "github.com/grafana/grafana-api-golang-client"
)

func main() {

	// fmt.Println(tgplugins.InputPlugins())
	tgplugins.InputPlugins()
	handlers.StartServer()

	handlers.GenerateConf()

	client, err := gapi.New("http://localhost:3000/", gapi.Config{BasicAuth: url.UserPassword("admin", "admin")})
	if err != nil {
		fmt.Printf("expected error to be nil; got: %s", err.Error())
	}

	dashboard, err := client.DashboardByUID("mannuvm")
	if err != nil {
		fmt.Printf("expected error to be nil; got: %s", err.Error())
	}

	fmt.Println(">>>>>>>>>>>>>>>>MANNUVMDASHBOARD>>>>>>>>>>>" + dashboard.Meta.URL)

	// dashb := gapi.Dashboard{
	// 	Model: map[string]interface{}{
	// 		"title": "testDashboard",
	// 	},
	// 	FolderID:  dashboard.Meta.Folder,
	// 	FolderUID: dashboard.FolderUID,
	// 	Overwrite: false,
	// }
	// resp, err := client.NewDashboard(dashb)
	// if err != nil {
	// 	fmt.Println(err)
	// }

	// fmt.Println(resp)

	// if resp.UID != "nErXDvCkzz" {
	// 	fmt.Println("Invalid uid - %s, Expected %s", resp.UID, "nErXDvCkzz")
	// }

	panel := gapi.LibraryPanel{
		Folder: dashboard.Meta.Folder,
		Name:   "API docs Example",
		Model:  map[string]interface{}{"description": "", "type": ""},
	}

	resp1, err := client.NewLibraryPanel(panel)
	if err != nil {
		fmt.Println(err)
	}

	fmt.Println(resp1)

}
