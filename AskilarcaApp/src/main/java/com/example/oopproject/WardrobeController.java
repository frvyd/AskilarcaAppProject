package com.example.oopproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Random;

public class WardrobeController implements Initializable {


    @FXML
    private TableColumn<Clothes,String> categoryColumn;

    @FXML
    private TextField categoryTextField;

    @FXML
    private TableColumn<Clothes,String> colorColumn;

    @FXML
    private TextField colorTextField;

    @FXML
    private Label failedChangesLabel;

    @FXML
    private Label ustGiysiLabel;

    @FXML
    private Label altGiysiLabel;

    @FXML
    private Label aksesuarLabel;

    @FXML
    private TableColumn<Clothes,String> materialColumn;

    @FXML
    private TextField materialTextField;

    @FXML
    private TableColumn<Clothes,String> sizeColumn;

    @FXML
    private TextField sizeTextField;

    @FXML
    private TableColumn<Clothes,String> typeColumn;

    @FXML
    private TextField typeTextField;

    @FXML
    private Button wardrobeDeleteButton;

    @FXML
    private Button wardrobeUpdateButton;

    @FXML
    private Button wardrobeAddButton;

    @FXML
    private Button wardrobeCombineButton;

    @FXML
    private TableView<Clothes> wardrobeTableView;

    private final ObservableList<Clothes> wardrobeData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeColumn.setCellValueFactory(new PropertyValueFactory<Clothes,String>("type"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Clothes,String>("category"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<Clothes,String>("color"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<Clothes,String>("material"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<Clothes,String>("size"));
    }

    @FXML
    void giysiTikla(MouseEvent event){
        int selectedIndex=wardrobeTableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex<=-1){
            return;
        }
        // T??klanan sat??r??n bilgilerini textfield'lara doldur
        typeTextField.setText(typeColumn.getCellData(selectedIndex));
        categoryTextField.setText(categoryColumn.getCellData(selectedIndex));
        colorTextField.setText(colorColumn.getCellData(selectedIndex));
        materialTextField.setText(materialColumn.getCellData(selectedIndex));
        sizeTextField.setText(sizeColumn.getCellData(selectedIndex));

    }
    File clothesfile = new File("clothes.txt");

    void dosyaKontrol() throws IOException {
        if(clothesfile.exists()) // Dosyan??n var olup olmad?????? kontrol edilir.
        {
            System.out.println("Dosya zaten mevcut");
        }
        else {
            clothesfile.createNewFile(); // Dosya yok ise olu??turur.
            System.out.println("Dosya olu??turuldu.");
        }
    }

    // clothes.txt den veri alma ve wardrobeData listesine ekleme
    public void veriCek() throws FileNotFoundException {
        String file = "clothes.txt";
        String FieldDelimiter = ",";       // sat??rlar "," delimiter ile b??l??n??r.

        FileReader fr = new FileReader(file);
        BufferedReader br =new BufferedReader(fr);

        try {
            String satir;

            while ((satir = br.readLine()) != null) {
                String[] index = satir.split(FieldDelimiter, -1);

                Clothes kay??t = new Clothes(index[0],index[1],index[2],index[3],index[4]);
                wardrobeData.add(kay??t);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void clothesAdd(ActionEvent event) throws IOException{
        dosyaKontrol();
        boolean eklemek = true;
        BufferedWriter bw = new BufferedWriter(new FileWriter("clothes.txt",true));
        String clothestype = typeTextField.getText();
        String clothescategory = categoryTextField.getText();           //textFieldlar?? de??i??kenlere atama
        String clothescolor = colorTextField.getText();
        String clothesmaterial = materialTextField.getText();
        String clothessize = sizeTextField.getText();


        if (typeTextField.getText().isEmpty() || categoryTextField.getText().isEmpty() || colorTextField.getText().isEmpty() || materialTextField.getText().isEmpty() || sizeTextField.getText().isEmpty()) {
            failedChangesLabel.setText("Hi??bir alan bo?? b??rak??lamaz!");

        }else if (eklemek == false) {
            failedChangesLabel.setText("Bu k??yafet zaten mevcut!");

        }else { // Yeni k??yafeti ekler, dosyaya kaydeder.
            wardrobeData.add(new Clothes(clothestype,clothescategory,clothescolor,clothesmaterial,clothessize));
            bw.write(clothestype + "," + clothescategory + "," + clothescolor + "," + clothesmaterial + "," + clothessize + "\n");

            failedChangesLabel.setText("K??yafet eklenmi??tir, tabloyu yenileyiniz.");

            typeTextField.setText("");
            categoryTextField.setText("");
            colorTextField.setText("");
            materialTextField.setText("");
            sizeTextField.setText("");
        }
        wardrobeTableView.getItems();
        wardrobeTableView.setItems(wardrobeData);
        bw.close();
    }

    @FXML
    void clothesDelete(ActionEvent event) throws IOException {
        Clothes selectedClothes=wardrobeTableView.getSelectionModel().getSelectedItem();  // Silinecek sat??r?? se??eriz
        if(selectedClothes !=null){
            wardrobeData.remove(selectedClothes);                      // wardrobeData listesinden kald??r??l??r ve tablodaki se??im temizlenir.
            wardrobeTableView.getSelectionModel().clearSelection();
            failedChangesLabel.setText("Se??ilen k??yafetiniz silindi.");
        }else{
            failedChangesLabel.setText("Silinecek k??yafet se??mediniz!");
        }

        ObservableList<Clothes> allData,SingleData;
        allData = wardrobeTableView.getItems();
        SingleData=wardrobeTableView.getSelectionModel().getSelectedItems();
        SingleData.forEach(allData::remove);

        //BufferedWriter kullanarak dosyaya tablodaki bilgileri kullanarak veri yazmak i??in dosyadaki t??m verileri temizler. ??st??ne yaz??lmamas?? i??in
        File file = new File("clothes.txt");
        PrintWriter pw = new PrintWriter("clothes.txt");
        pw.write("");
        pw.close();

        int topi = wardrobeTableView.getItems().size();
        BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
        for (int i = 0; i < topi; i++) {
            bw.write(typeColumn.getCellData(i)+","+ categoryColumn.getCellData(i) +","+ colorColumn.getCellData(i)+","+materialColumn.getCellData(i)+","+sizeColumn.getCellData(i)+"\n");
        }
        bw.close();
        wardrobeTableView.getItems();                       //tablodaki ????eleri g??ncellenmi?? wardrobeData listesine ayarlar.
        wardrobeTableView.setItems(wardrobeData);

    }
    @FXML
    void clothesUpdate(ActionEvent event) throws FileNotFoundException {
        wardrobeTableView.getItems().clear();
        wardrobeData.clear();
        veriCek();
        wardrobeTableView.setItems(wardrobeData);
        System.out.println("Tablo Yenilendi.");
        failedChangesLabel.setText("Gardrop tablonuz yenilendi.");
    }

/*
    Random s??n??f??ndan bir nesne olu??turulur
    wardrobeData dizisinden rastgele ??st, alt giysi ve aksesuar se??ilir
    se??ilen giysiler labellarda g??sterilir.
*/
    @FXML        // Kullan??c??n??n rastgele ??st, alt ve aksesuar giysileri se??mesini sa??lar.
    void makeCombine(ActionEvent event) {
        Random rand = new Random();
        Clothes ustGiysi = wardrobeData.get(rand.nextInt(wardrobeData.size()));
        Clothes altGiysi = wardrobeData.get(rand.nextInt(wardrobeData.size()));
        Clothes aksesuar = wardrobeData.get(rand.nextInt(wardrobeData.size()));

        ustGiysiLabel.setText(ustGiysi.getType()+" "+ustGiysi.getCategory()+" "+ustGiysi.getColor()+" "+ustGiysi.getMaterial()+" "+ustGiysi.getSize());
        altGiysiLabel.setText(altGiysi.getType()+" "+altGiysi.getCategory()+" "+altGiysi.getColor()+" "+altGiysi.getMaterial()+" "+altGiysi.getSize());
        aksesuarLabel.setText(aksesuar.getType()+" "+aksesuar.getCategory()+" "+aksesuar.getColor()+" "+aksesuar.getMaterial()+" "+aksesuar.getSize());

        Clothes randomClothes=wardrobeData.get(rand.nextInt(wardrobeData.size()));
        while(!randomClothes.getType().equalsIgnoreCase("??st")) {
            randomClothes = wardrobeData.get(rand.nextInt(wardrobeData.size()));
        }
        ustGiysiLabel.setText(randomClothes.getType()+"\n"+randomClothes.getCategory()+"\n"+randomClothes.getColor()+"\n"+randomClothes.getMaterial()+"\n"+randomClothes.getSize());
        randomClothes = wardrobeData.get(rand.nextInt(wardrobeData.size()));

        while(!randomClothes.getType().equalsIgnoreCase("alt")) {
            randomClothes = wardrobeData.get(rand.nextInt(wardrobeData.size()));
        }
        altGiysiLabel.setText(randomClothes.getType()+"\n"+randomClothes.getCategory()+"\n"+randomClothes.getColor()+"\n"+randomClothes.getMaterial()+"\n"+randomClothes.getSize());
        randomClothes = wardrobeData.get(rand.nextInt(wardrobeData.size()));

        while(!randomClothes.getType().equalsIgnoreCase("aksesuar")) {
            randomClothes = wardrobeData.get(rand.nextInt(wardrobeData.size()));
        }
        aksesuarLabel.setText(randomClothes.getType()+"\n"+randomClothes.getCategory()+"\n"+randomClothes.getColor()+"\n"+randomClothes.getMaterial()+"\n"+randomClothes.getSize());

    }
}
