package com.ecoforma.frontend.forms;

import com.ecoforma.db.entities.*;
import com.ecoforma.db.services.StoreService;
import com.ecoforma.frontend.CompanyFrame;
import com.ecoforma.frontend.services.Checker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class StoreForm {
    CompanyFrame frame;
    private JTable tableProduct, tableStore;
    private JTextField tfSearchInProducts, tfSearchInStore;
    private JRadioButton rbName, rbCategory;
    private JTextField tfName;
    private JSpinner spinnerCost;
    private JComboBox cbbxCategory;
    private JTextArea textAreaCharacteristics;
    private JButton btnAddToStore, btnInsertProduct, btnUpdateProduct, btnDeleteFromProduct;
    private JSpinner spinnerIncreaseProduct, spinnerDecreaseProduct, spinnerMoveProduct;
    private JComboBox cbbxOtherStores;
    private JButton btnAcceptIncrease, btnAcceptDecrease, btnAcceptMove, btnDeleteFromStore;
    private JScrollPane tableProductScroll, tableStoreScroll;

    private String[] tableProductHeader = new String[] { "Код товара", "Название", "Категория", "Стоимость" };
    private String[] tableStoreHeader = new String[] { "Код записи о хранении", "Название", "Количество" };

    private Checker checker;

    private StoreService dbService;

    private Store currentStore;
    private Product currentProduct;
    private ProductToStore currentProductToStore;

    StoreForm(String login, String password) throws IOException {
        dbService = new StoreService();
        checker = new Checker();

        currentStore = dbService.getStore(login, password);

        frame = new CompanyFrame(currentStore.getName());

        JPanel panelTables = frame.factory.newPanelDefault(10, 55, 1336, 406);
        frame.add(panelTables);

        JPanel panelTableProduct = frame.factory.newPanelBevelTable(10, 37, 647, 358);
        panelTables.add(panelTableProduct);

        JPanel panelTableStore = frame.factory.newPanelBevelTable(670, 37, 647, 358);
        panelTables.add(panelTableStore);

        JLabel lTableProduct = frame.factory.newLabel("Общий каталог товаров", new Rectangle(10, 11, 647, 20));
        panelTables.add(lTableProduct);

        JLabel lTableStore = frame.factory.newLabel("Товары на складе", new Rectangle(670, 11, 647, 20));
        panelTables.add(lTableStore);

        tableProduct = frame.factory.newTable(setInitialTableProductModel());

        tableProductScroll = frame.factory.newTableScroll(tableProduct, 627, 336);
        panelTableProduct.add(tableProductScroll);
        addUnpickProductEscape();

        tableStore = frame.factory.newTable(setInitialTableStoreModel());

        tableStoreScroll = frame.factory.newTableScroll(tableStore, 627, 336);
        panelTableStore.add(tableStoreScroll);
        addUnpickStoreEscape();

        JLabel lSearchInProducts = frame.factory.newLabel("Поиск в каталоге", new Rectangle(20, 455, 240, 20));
        frame.add(lSearchInProducts);

        JLabel lSearchInStore = frame.factory.newLabel("Поиск на складе", new Rectangle(680, 455, 240, 20));
        frame.add(lSearchInStore);

        tfSearchInProducts = frame.factory.newTextFieldEnabled(20, new Rectangle(20, 482, 189, 23));
        frame.add(tfSearchInProducts);

        JButton btnSearchInProducts = frame.factory.newButtonEnabled("Поиск", "icon-search", new Rectangle(219, 482, 93, 23));
        frame.add(btnSearchInProducts);

        JButton btnClearSearchProducts = frame.factory.newButtonEnabled(null, "icon-close", new Rectangle(320, 482, 24, 23));
        btnClearSearchProducts.setToolTipText("Очистка результов поиска");
        frame.add(btnClearSearchProducts);

        rbName = frame.factory.newRadioButton("Имя", "product.name", new Rectangle(352, 482, 56, 23));
        rbName.setSelected(true);
        frame.add(rbName);

        rbCategory = frame.factory.newRadioButton("Категория", "productCategory.name", new Rectangle(412, 482, 96, 23));
        frame.add(rbCategory);

        ButtonGroup searchGroup = new ButtonGroup();
        searchGroup.add(rbName);
        searchGroup.add(rbCategory);

        tfSearchInStore = frame.factory.newTextFieldEnabled(20, new Rectangle(680, 482, 189, 23));
        frame.add(tfSearchInStore);

        JButton btnSearchInStore = frame.factory.newButtonEnabled("Поиск", "icon-search", new Rectangle(879, 480, 93, 23));
        frame.add(btnSearchInStore);

        JButton btnClearSearchStore = frame.factory.newButtonEnabled(null, "icon-close", new Rectangle(980, 480, 24, 23));
        btnClearSearchStore.setToolTipText("Очистка результов поиска");
        frame.add(btnClearSearchStore);

        JPanel panelEditProduct = frame.factory.newPanelEtched(20, 516, 650, 220);
        frame.add(panelEditProduct);

        tfName = frame.factory.newTextFieldEnabled(40, new Rectangle(12, 12, 189, 23));
        tfName.setToolTipText("Название товара");
        panelEditProduct.add(tfName);

        spinnerCost = frame.factory.newSpinnerNumericEnabled(
                new SpinnerNumberModel(1, 1, 2999999, 100),
                new Rectangle(213, 13, 189, 22)
        );
        spinnerCost.setToolTipText("Стоимость товара");
        panelEditProduct.add(spinnerCost);

        cbbxCategory = frame.factory.newComboBox(
                dbService.getCategories(), new Rectangle(414, 11, 224, 25)
        );
        cbbxCategory.setToolTipText("Категория товара");
        cbbxCategory.setEnabled(true);
        panelEditProduct.add(cbbxCategory);

        textAreaCharacteristics = frame.factory.newTextAreaEnabled(12, 41, 626, 133, 100, 20);
        textAreaCharacteristics.setToolTipText("Описание характеристик товара");
        panelEditProduct.add(textAreaCharacteristics);

        btnAddToStore = frame.factory.newButton("На склад", "icon-move", new Rectangle(12, 183, 146, 26));
        panelEditProduct.add(btnAddToStore);

        btnInsertProduct = frame.factory.newButtonEnabled("В каталог", "icon-add", new Rectangle(169, 183, 142, 26));
        panelEditProduct.add(btnInsertProduct);

        btnUpdateProduct = frame.factory.newButton("Изменить", "icon-unfocus", new Rectangle(323, 183, 135, 26));
        panelEditProduct.add(btnUpdateProduct);

        btnDeleteFromProduct = frame.factory.newButton("Удалить", "icon-delete", new Rectangle(470, 183, 168, 26));
        panelEditProduct.add(btnDeleteFromProduct);

        JLabel lIncreaseProduct = frame.factory.newLabel("Добавить товар, ед.", new Rectangle(680, 517, 146, 20));
        frame.add(lIncreaseProduct);

        JLabel lDecreaseProduct = frame.factory.newLabel("Отпустить товар, ед.", new Rectangle(986, 516, 146, 20));
        frame.add(lDecreaseProduct);

        spinnerIncreaseProduct = frame.factory.newSpinnerNumericDisabled(
                new SpinnerNumberModel(0, 0, 1999999, 1),
                new Rectangle(844, 516, 124, 20)
                );
        frame.add(spinnerIncreaseProduct);

        spinnerDecreaseProduct = frame.factory.newSpinnerNumericDisabled(
                new SpinnerNumberModel(0, 0, 1999999, 1),
                new Rectangle(1150, 516, 124, 20)
        );
        frame.add(spinnerDecreaseProduct);

        btnAcceptIncrease = frame.factory.newButton("Подтвердить действие", "icon-accept", new Rectangle(680, 549, 288, 26));
        frame.add(btnAcceptIncrease);

        btnAcceptDecrease = frame.factory.newButton("Подтвердить действие", "icon-accept", new Rectangle(986, 549, 288, 26));
        frame.add(btnAcceptDecrease);

        btnDeleteFromStore = frame.factory.newButton("Удалить запись о хранении", "icon-delete", new Rectangle(986, 607, 288, 26));
        frame.add(btnDeleteFromStore);

        JLabel lMoveToOtherStore = frame.factory.newLabel("Переместить на склад:", new Rectangle(680, 587, 288, 16));
        frame.add(lMoveToOtherStore);

        cbbxOtherStores = frame.factory.newComboBox(
                dbService.getStoresBesides(currentStore.getID()),
                new Rectangle(680, 608, 288, 25)
        );
        frame.add(cbbxOtherStores);

        JLabel lQuantityMove = frame.factory.newLabel("В количестве, ед.", new Rectangle(680, 645, 124, 26));
        frame.add(lQuantityMove);

        spinnerMoveProduct = frame.factory.newSpinnerNumericDisabled(
                new SpinnerNumberModel(0, 0, 1999999, 1),
                new Rectangle(844, 648, 124, 20)
        );
        frame.add(spinnerMoveProduct);

        btnAcceptMove = frame.factory.newButton("Подтвердить действие", "icon-accept", new Rectangle(680, 683, 288, 26));
        frame.add(btnAcceptMove);

        ListSelectionModel selectionModelProduct = tableProduct.getSelectionModel();
        selectionModelProduct.addListSelectionListener(listSelectionEvent -> prepareToEditProduct());

        ListSelectionModel selectionModelStore = tableStore.getSelectionModel();
        selectionModelStore.addListSelectionListener(listSelectionEvent -> prepareToEditStore());

        btnInsertProduct.addActionListener(actionEvent -> insertProductToCatalogue());

        btnUpdateProduct.addActionListener(actionEvent -> updateProduct());

        btnDeleteFromProduct.addActionListener(actionEvent -> deleteFromProduct());

        btnAddToStore.addActionListener(actionEvent -> addToStore());

        btnDeleteFromStore.addActionListener(actionEvent -> deleteFromStore());

        btnAcceptIncrease.addActionListener(actionEvent -> increaseProduct());

        btnAcceptDecrease.addActionListener(actionEvent -> decreaseProduct());

        btnAcceptMove.addActionListener(actionEvent -> moveToOtherStore());

        btnSearchInProducts.addActionListener(actionEvent -> searchInProducts());

        btnSearchInStore.addActionListener(actionEvent -> searchInStore());

        tfSearchInProducts.addActionListener(actionEvent -> searchInProducts());

        tfSearchInStore.addActionListener(actionEvent -> searchInStore());

        btnClearSearchProducts.addActionListener(actionEvent -> {
            unpickProduct();
            tfSearchInProducts.setText("");
        });

        btnClearSearchStore.addActionListener(actionEvent -> {
            unpickStore();
            tfSearchInStore.setText("");
        });
    }

    private DefaultTableModel setInitialTableProductModel() {
        DefaultTableModel initialTableProductModel = new DefaultTableModel();
        initialTableProductModel.setColumnIdentifiers(tableProductHeader);

        ArrayList<ProductView> products = dbService.getProductView();

        for (int i = 0; i < products.size(); i++) {
            initialTableProductModel.insertRow(i, new Object[] {
                    products.get(i).getID(),
                    products.get(i).getName(),
                    products.get(i).getCategory(),
                    products.get(i).getCost()
            });
        }

        return initialTableProductModel;
    }

    private DefaultTableModel setInitialTableStoreModel() {
        DefaultTableModel initialTableStoreModel = new DefaultTableModel();
        initialTableStoreModel.setColumnIdentifiers(tableStoreHeader);

        ArrayList<StoreView> productsOnThisStore = dbService.getStoreView(currentStore.getID());

        for (int i = 0; i < productsOnThisStore.size(); i++) {
            initialTableStoreModel.insertRow(i, new Object[] {
                    productsOnThisStore.get(i).getID(),
                    productsOnThisStore.get(i).getName(),
                    productsOnThisStore.get(i).getCount()
            });
        }

        return initialTableStoreModel;
    }

    private void prepareToEditProduct() {
        int rowIndex = tableProduct.getSelectedRow();
        currentProduct = dbService.getProductByID(Integer.parseInt(tableProduct.getModel().getValueAt(rowIndex, 0).toString()));

        tfName.setText(currentProduct.getName());
        spinnerCost.setValue(currentProduct.getCost());
        cbbxCategory.setSelectedIndex(currentProduct.getProductCategory_ID() - 1);
        textAreaCharacteristics.setText(currentProduct.getCharacteristics());

        btnInsertProduct.setEnabled(false);
        btnUpdateProduct.setEnabled(true);
        btnDeleteFromProduct.setEnabled(true);
        btnAddToStore.setEnabled(true);
    }

    private void prepareToEditStore() {
        int rowIndex = tableStore.getSelectedRow();
        currentProductToStore = dbService.getProductToStoreByID(Integer.parseInt(tableStore.getModel().getValueAt(rowIndex, 0).toString()));

        spinnerIncreaseProduct.setEnabled(true);
        spinnerDecreaseProduct.setEnabled(true);
        spinnerMoveProduct.setEnabled(true);
        cbbxOtherStores.setEnabled(true);
        btnAcceptIncrease.setEnabled(true);
        btnAcceptDecrease.setEnabled(true);
        btnAcceptMove.setEnabled(true);
        btnDeleteFromStore.setEnabled(true);
    }

    private void removeFocusFromTableProduct() {
        tableProductScroll.setViewportView(null);

        tableProduct = frame.factory.newTable(setInitialTableProductModel());
        tableProductScroll.setViewportView(tableProduct);
        ListSelectionModel selectionModelProduct = tableProduct.getSelectionModel();
        selectionModelProduct.addListSelectionListener(listSelectionEvent -> prepareToEditProduct());
        addUnpickProductEscape();
    }

    private void removeFocusFromTableStore() {
        tableStoreScroll.setViewportView(null);

        tableStore = frame.factory.newTable(setInitialTableStoreModel());
        tableStoreScroll.setViewportView(tableStore);
        ListSelectionModel selectionModelStore = tableStore.getSelectionModel();
        selectionModelStore.addListSelectionListener(listSelectionEvent -> prepareToEditStore());
        addUnpickStoreEscape();
    }

    private void unpickProduct() {
        removeFocusFromTableProduct();

        tfName.setText("");
        spinnerCost.setValue(1);
        cbbxCategory.setSelectedIndex(0);
        textAreaCharacteristics.setText("");

        btnInsertProduct.setEnabled(true);
        btnUpdateProduct.setEnabled(false);
        btnDeleteFromProduct.setEnabled(false);
        btnAddToStore.setEnabled(false);

        currentProduct = null;
    }

    private void unpickStore() {
        removeFocusFromTableStore();

        spinnerIncreaseProduct.setEnabled(false);
        spinnerDecreaseProduct.setEnabled(false);
        spinnerMoveProduct.setEnabled(false);
        cbbxOtherStores.setEnabled(false);
        btnAcceptIncrease.setEnabled(false);
        btnAcceptDecrease.setEnabled(false);
        btnAcceptMove.setEnabled(false);
        btnDeleteFromStore.setEnabled(false);

        spinnerIncreaseProduct.setValue(0);
        spinnerDecreaseProduct.setValue(0);
        spinnerMoveProduct.setValue(0);

        cbbxOtherStores.setSelectedIndex(0);
    }

    private void addUnpickStoreEscape() {
        tableStore.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    unpickStore();
                }
            }
        });
    }

    private void addUnpickProductEscape() {
        tableProduct.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    unpickProduct();
                }
            }
        });
    }

    private void insertProductToCatalogue() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Добавить новый товар " + tfName.getText() + "?",
                "Подтверждение операции",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            ArrayList<Product> productsWithThisName = dbService.getProductsByName(tfName.getText());

            if (productsWithThisName.size() != 0) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Товар с таким именем уже существует в базе данных.",
                        "Ошибка при добавлении",
                        JOptionPane.WARNING_MESSAGE
                );
            } else if (
                    checker.checkTextField(tfName.getText(), tfName.getColumns()) &&
                    checker.checkTextField(textAreaCharacteristics.getText(), 1000)
            ) {
                dbService.insertProduct(
                        tfName.getText(),
                        textAreaCharacteristics.getText(),
                        (Integer) spinnerCost.getValue(),
                        cbbxCategory.getSelectedIndex() + 1
                );

                JOptionPane.showMessageDialog(
                        frame,
                        "Товар успешно добавлен.",
                        "Добавление завершено",
                        JOptionPane.INFORMATION_MESSAGE
                );
                unpickProduct();
            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "Одно из полей пустое или содержит недопустимое значение.",
                        "Ошибка при добавлении",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }

    private void updateProduct() {
        if (
                checker.checkTextField(tfName.getText(), tfName.getColumns()) &&
                checker.checkTextField(textAreaCharacteristics.getText(), 1000)
        ) {
            dbService.updateProduct(
                    currentProduct.getID(),
                    tfName.getText(),
                    textAreaCharacteristics.getText(),
                    (Integer) spinnerCost.getValue(),
                    cbbxCategory.getSelectedIndex() + 1
            );

            unpickProduct();
            unpickStore();
        } else {
            JOptionPane.showMessageDialog(
                    frame,
                    "Одно из полей пустое или содержит недопустимое значение.",
                    "Ошибка при обновлении",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void deleteFromProduct() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Вы уверены, что хотите удалить " + currentProduct.getName() + "?",
                "Подтверждение операции",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            dbService.deleteProduct(currentProduct.getID());

            ArrayList<ProductToStore> productInStores = dbService.getProductToStore(
                    currentProduct.getID(), currentStore.getID()
            );

            if (productInStores.size() != 0) {
                dbService.deleteProductFromAllStores(currentProduct.getID());
            }

            JOptionPane.showMessageDialog(
                    frame,
                    "Товар успешно удалён.",
                    "Удаление завершено",
                    JOptionPane.INFORMATION_MESSAGE
            );
            currentProduct = null;
            unpickProduct();
            unpickStore();
        }
    }

    private void addToStore() {
        ArrayList<ProductToStore> currentProductOnStore = dbService.getProductToStore(currentProduct.getID(), currentStore.getID());

        if (currentProductOnStore.size() != 0) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Товар уже существует на складе.",
                    "Ошибка при добавлении",
                    JOptionPane.WARNING_MESSAGE
            );
        } else {
            dbService.insertProductToStore(currentStore.getID(), currentProduct.getID(), 1);

            JOptionPane.showMessageDialog(
                    frame,
                    "Товар добавлен на склад.",
                    "Добавление завершено",
                    JOptionPane.INFORMATION_MESSAGE
            );
            unpickStore();
        }
    }

    private void deleteFromStore() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Вы уверены, что хотите удалить запись о хранении?",
                "Подтверждение операции",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            dbService.deleteProductFromStore(currentProductToStore.getID());

            JOptionPane.showMessageDialog(
                    frame,
                    "Запись удалена.",
                    "Удаление завершено",
                    JOptionPane.INFORMATION_MESSAGE
            );
            unpickStore();
        }
    }

    private void increaseProduct() {
        currentProductToStore.setCount(currentProductToStore.getCount() + (int) spinnerIncreaseProduct.getValue());
        dbService.updateCount(currentProductToStore.getID(), currentProductToStore.getCount());

        unpickStore();
        spinnerIncreaseProduct.setValue(0);
    }

    private void decreaseProduct() {
        if ((currentProductToStore.getCount() - (int) spinnerDecreaseProduct.getValue() < 0)) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Вы не можете отпустить такое количество товаров.",
                    "Ошибка при изменении",
                    JOptionPane.WARNING_MESSAGE
            );
        } else {
            currentProductToStore.setCount(currentProductToStore.getCount() - (int) spinnerDecreaseProduct.getValue());
            dbService.updateCount(currentProductToStore.getID(), currentProductToStore.getCount());

            unpickStore();
            spinnerDecreaseProduct.setValue(0);
        }
    }

    private void moveToOtherStore() {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Вы уверены, что хотите переместить товар?",
                "Подтверждение операции",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            if (currentProductToStore.getCount() - (int) spinnerMoveProduct.getValue() < 0) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Вы не можете отпустить такое количество товаров.",
                        "Ошибка при изменении",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if ((int) spinnerMoveProduct.getValue() > 0) {
                int storeID = dbService.getStoreIDByName(Objects.requireNonNull(cbbxOtherStores.getSelectedItem()).toString());
                ArrayList<ProductToStore> productsInOtherStory = dbService.getProductToStore(currentProductToStore.getProduct_ID(), storeID);

                if (productsInOtherStory.size() != 0) {
                    dbService.updateCount(
                            productsInOtherStory.get(0).getID(),
                            productsInOtherStory.get(0).getCount() + (int) spinnerMoveProduct.getValue()
                    );
                } else {
                    dbService.insertProductToStore(
                            storeID,
                            currentProductToStore.getProduct_ID(),
                            (int) spinnerMoveProduct.getValue()
                    );
                }
                dbService.updateCount(currentProductToStore.getID(),
                currentProductToStore.getCount() - (int) spinnerMoveProduct.getValue());

                JOptionPane.showMessageDialog(
                        frame,
                        "Товар перемещён.",
                        "Добавление завершено",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "Вы не можете переместить со склада 0 единиц товара.",
                        "Ошибка при изменении",
                        JOptionPane.WARNING_MESSAGE
                );
            }
            unpickStore();
        }
    }

    private void searchInProducts() {
        unpickProduct();

        String column = "";
        String query = "'%" + tfSearchInProducts.getText() + "%'";
        String option = "";

        if (rbName.isSelected()) {
            column = rbName.getActionCommand();
            option = rbName.getText();
        } else if (rbCategory.isSelected()) {
            column = rbCategory.getActionCommand();
            option = rbCategory.getText();
        }

        ArrayList<ProductView> result = dbService.searchProductView(column, query);

        if (result.size() == 0) {
            JOptionPane.showMessageDialog(frame,
                    "По запросу \"" + tfSearchInProducts.getText() + "\" по критерию \"" + option +
                            "\" ничего не найдено.\nПопробуйте уточнить запрос или изменить критерий поиска.", "Не найдено",
                    JOptionPane.INFORMATION_MESSAGE
            );
            tfSearchInProducts.setText("");
        } else {
            DefaultTableModel searchTableModel = new DefaultTableModel();
            searchTableModel.setColumnIdentifiers(tableProductHeader);

            for (int i = 0; i < result.size(); i++) {
                searchTableModel.insertRow(i, new Object[]{
                        result.get(i).getID(),
                        result.get(i).getName(),
                        result.get(i).getCategory(),
                        result.get(i).getCost()
                });
            }
            tableProduct.setModel(searchTableModel);
        }
    }

    private void searchInStore() {
        unpickStore();

        String query = "'%" + tfSearchInStore.getText() + "%'";

        ArrayList<StoreView> result = dbService.searchStoreView(currentStore.getID(), query);

        if (result.size() == 0) {
            JOptionPane.showMessageDialog(frame,
                    "По запросу \"" + tfSearchInStore.getText() +
                            "\" ничего не найдено.\nПопробуйте уточнить запрос.", "Не найдено",
                    JOptionPane.INFORMATION_MESSAGE
            );
            tfSearchInProducts.setText("");
        } else {
            DefaultTableModel searchTableModel = new DefaultTableModel();
            searchTableModel.setColumnIdentifiers(tableStoreHeader);

            for (int i = 0; i < result.size(); i++) {
                searchTableModel.insertRow(i, new Object[]{
                        result.get(i).getID(),
                        result.get(i).getName(),
                        result.get(i).getCount()
                });
            }
            tableStore.setModel(searchTableModel);
        }
    }
}
