package com.ecoforma.forms;

import com.ecoforma.db.DbSession;
import com.ecoforma.db.mappers.StoreMapper;
import com.ecoforma.entities.*;
import com.ecoforma.services.Checker;
import com.ecoforma.services.CommonActivity;
import com.ecoforma.services.Initializer;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import static com.ecoforma.App.COMPANY_NAME;

public class StoreForm {
    JFrame frame;
    private JToolBar toolBar;
    private JButton btnSignOut;
    private JTable tableProduct, tableStore;
    private JTextField tfSearchInProducts, tfSearchInStore;
    private JButton btnSearchInProducts, btnSearchInStore, btnClearSearchProducts, btnClearSearchStore;
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

    private DefaultTableModel initialTableProductModel, initialTableStoreModel;
    private String[] tableProductHeader = new String[] { "Код товара", "Название", "Категория", "Стоимость" };
    private String[] tableStoreHeader = new String[] { "Код записи о хранении", "Название", "Количество" };

    private Initializer initializer;
    private Checker checker;

    private Store currentStore;
    private Product currentProduct;
    private ProductToStore currentProductToStore;

    StoreForm(String login, String password) throws IOException {
        initializer = new Initializer();
        checker = new Checker();

        CommonActivity activity = new CommonActivity();

        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            currentStore = mapper.getStore(login, password);
        }

        frame = initializer.newFrame(COMPANY_NAME + " - " + currentStore.getName(), new Rectangle(323,  144, 1362, 790), JFrame.EXIT_ON_CLOSE);

        toolBar = initializer.newToolBar(0, 0, 1346, 44);
        frame.getContentPane().add(toolBar);

        btnSignOut = initializer.newButtonEnabled("Выход из системы", "icon-logout", new Rectangle(0, 0, 1346, 44));
        toolBar.add(btnSignOut);

        JPanel panelTables = initializer.newPanelDefault(10, 55, 1336, 406);
        frame.add(panelTables);

        JPanel panelTableProduct = initializer.newPanelBevelTable(10, 37, 647, 358);
        panelTables.add(panelTableProduct);

        JPanel panelTableStore = initializer.newPanelBevelTable(670, 37, 647, 358);
        panelTables.add(panelTableStore);

        JLabel lTableProduct = initializer.newLabel("Общий каталог товаров", new Rectangle(10, 11, 647, 20));
        panelTables.add(lTableProduct);

        JLabel lTableStore = initializer.newLabel("Товары на складе", new Rectangle(670, 11, 647, 20));
        panelTables.add(lTableStore);

        tableProduct = initializer.newTable(setInitialTableProductModel());

        tableProductScroll = initializer.newTableScroll(tableProduct, 627, 336);
        panelTableProduct.add(tableProductScroll);
        addUnpickProductEscape();

        tableStore = initializer.newTable(setInitialTableStoreModel());

        tableStoreScroll = initializer.newTableScroll(tableStore, 627, 336);
        panelTableStore.add(tableStoreScroll);
        addUnpickStoreEscape();

        JLabel lSearchInProducts = initializer.newLabel("Поиск в каталоге", new Rectangle(20, 455, 240, 20));
        frame.add(lSearchInProducts);

        JLabel lSearchInStore = initializer.newLabel("Поиск на складе", new Rectangle(680, 455, 240, 20));
        frame.add(lSearchInStore);

        tfSearchInProducts = initializer.newTextFieldEnabled(20, new Rectangle(20, 482, 189, 23));
        frame.add(tfSearchInProducts);

        btnSearchInProducts = initializer.newButtonEnabled("Поиск", "icon-search", new Rectangle(219, 482, 93, 23));
        frame.add(btnSearchInProducts);

        btnClearSearchProducts = initializer.newButtonEnabled(null, "icon-close", new Rectangle(320, 482, 24, 23));
        btnClearSearchProducts.setToolTipText("Очистка результов поиска");
        frame.add(btnClearSearchProducts);

        rbName = initializer.newRadioButton("Имя", "product.name", new Rectangle(352, 482, 56, 23));
        rbName.setSelected(true);
        frame.add(rbName);

        rbCategory = initializer.newRadioButton("Категория", "productCategory.name", new Rectangle(412, 482, 96, 23));
        frame.add(rbCategory);

        ButtonGroup searchGroup = new ButtonGroup();
        searchGroup.add(rbName);
        searchGroup.add(rbCategory);

        tfSearchInStore = initializer.newTextFieldEnabled(20, new Rectangle(680, 482, 189, 23));
        frame.add(tfSearchInStore);

        btnSearchInStore = initializer.newButtonEnabled("Поиск", "icon-search", new Rectangle(879, 480, 93, 23));
        frame.add(btnSearchInStore);

        btnClearSearchStore = initializer.newButtonEnabled(null, "icon-close", new Rectangle(980, 480, 24, 23));
        btnClearSearchStore.setToolTipText("Очистка результов поиска");
        frame.add(btnClearSearchStore);

        JPanel panelEditProduct = initializer.newPanelEtched(20, 516, 650, 220);
        frame.add(panelEditProduct);

        tfName = initializer.newTextFieldEnabled(40, new Rectangle(12, 12, 189, 23));
        tfName.setToolTipText("Название товара");
        panelEditProduct.add(tfName);

        spinnerCost = initializer.newSpinnerNumericEnabled(
                new SpinnerNumberModel(1, 1, 2999999, 100),
                new Rectangle(213, 13, 189, 22)
        );
        spinnerCost.setToolTipText("Стоимость товара");
        panelEditProduct.add(spinnerCost);

        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            cbbxCategory = initializer.newComboBox(
                    mapper.getCategories(), new Rectangle(414, 11, 224, 25)
            );
            cbbxCategory.setToolTipText("Категория товара");
            cbbxCategory.setEnabled(true);
            panelEditProduct.add(cbbxCategory);
        }

        textAreaCharacteristics = initializer.newTextAreaEnabled(12, 41, 626, 133, 100, 20);
        textAreaCharacteristics.setToolTipText("Описание характеристик товара");
        panelEditProduct.add(textAreaCharacteristics);

        btnAddToStore = initializer.newButton("На склад", new Rectangle(12, 183, 146, 26));
        panelEditProduct.add(btnAddToStore);

        btnInsertProduct = initializer.newButtonEnabled("В каталог", new Rectangle(169, 183, 142, 26));
        panelEditProduct.add(btnInsertProduct);

        btnUpdateProduct = initializer.newButton("Изменить товар", new Rectangle(323, 183, 135, 26));
        panelEditProduct.add(btnUpdateProduct);

        btnDeleteFromProduct = initializer.newButton("Удалить из каталога", new Rectangle(470, 183, 168, 26));
        panelEditProduct.add(btnDeleteFromProduct);

        JLabel lIncreaseProduct = initializer.newLabel("Добавить товар, ед.", new Rectangle(680, 517, 146, 20));
        frame.add(lIncreaseProduct);

        JLabel lDecreaseProduct = initializer.newLabel("Отпустить товар, ед.", new Rectangle(986, 516, 146, 20));
        frame.add(lDecreaseProduct);

        spinnerIncreaseProduct = initializer.newSpinnerNumericDisabled(
                new SpinnerNumberModel(0, 0, 1999999, 1),
                new Rectangle(844, 516, 124, 20)
                );
        frame.add(spinnerIncreaseProduct);

        spinnerDecreaseProduct = initializer.newSpinnerNumericDisabled(
                new SpinnerNumberModel(0, 0, 1999999, 1),
                new Rectangle(1150, 516, 124, 20)
        );
        frame.add(spinnerDecreaseProduct);

        btnAcceptIncrease = initializer.newButton("Подтвердить действие", new Rectangle(680, 549, 288, 26));
        frame.add(btnAcceptIncrease);

        btnAcceptDecrease = initializer.newButton("Подтвердить действие", new Rectangle(986, 549, 288, 26));
        frame.add(btnAcceptDecrease);

        btnDeleteFromStore = initializer.newButton("Удалить запись о хранении", new Rectangle(986, 607, 288, 26));
        frame.add(btnDeleteFromStore);

        JLabel lMoveToOtherStore = initializer.newLabel("Переместить на склад:", new Rectangle(680, 587, 288, 16));
        frame.add(lMoveToOtherStore);

        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            cbbxOtherStores = initializer.newComboBox(mapper.getStoresBesides(currentStore.getID()), new Rectangle(680, 608, 288, 25));
            frame.add(cbbxOtherStores);
        }

        JLabel lQuantityMove = initializer.newLabel("В количестве, ед.", new Rectangle(680, 645, 124, 26));
        frame.add(lQuantityMove);

        spinnerMoveProduct = initializer.newSpinnerNumericDisabled(
                new SpinnerNumberModel(0, 0, 1999999, 1),
                new Rectangle(844, 648, 124, 20)
        );
        frame.add(spinnerMoveProduct);

        btnAcceptMove = initializer.newButton("Подтвердить действие", new Rectangle(680, 683, 288, 26));
        frame.add(btnAcceptMove);

        ListSelectionModel selectionModelProduct = tableProduct.getSelectionModel();
        selectionModelProduct.addListSelectionListener(listSelectionEvent -> prepareToEditProduct());

        ListSelectionModel selectionModelStore = tableStore.getSelectionModel();
        selectionModelStore.addListSelectionListener(listSelectionEvent -> prepareToEditStore());

        btnSignOut.addActionListener(actionEvent -> activity.signOut(frame));

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
        initialTableProductModel = new DefaultTableModel();
        initialTableProductModel.setColumnIdentifiers(tableProductHeader);

        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            ArrayList<ProductView> products = mapper.getProductView();

            for (int i = 0; i < products.size(); i++) {
                initialTableProductModel.insertRow(i, new Object[] {
                        products.get(i).getID(),
                        products.get(i).getName(),
                        products.get(i).getCategory(),
                        products.get(i).getCost()
                });
            }
        }

        return initialTableProductModel;
    }

    private DefaultTableModel setInitialTableStoreModel() {
        initialTableStoreModel = new DefaultTableModel();
        initialTableStoreModel.setColumnIdentifiers(tableStoreHeader);

        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            ArrayList<StoreView> productsOnThisStore = mapper.getStoreView(currentStore.getID());

            for (int i = 0; i < productsOnThisStore.size(); i++) {
                initialTableStoreModel.insertRow(i, new Object[] {
                        productsOnThisStore.get(i).getID(),
                        productsOnThisStore.get(i).getName(),
                        productsOnThisStore.get(i).getCount()
                });
            }
        }

        return initialTableStoreModel;
    }

    private void prepareToEditProduct() {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);

            int rowIndex = tableProduct.getSelectedRow();
            currentProduct = mapper.getProductByID(Integer.parseInt(tableProduct.getModel().getValueAt(rowIndex, 0).toString()));
        }

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
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);

            int rowIndex = tableStore.getSelectedRow();
            currentProductToStore = mapper.getProductToStoreByID(Integer.parseInt(tableStore.getModel().getValueAt(rowIndex, 0).toString()));
        }

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

        tableProduct = initializer.newTable(setInitialTableProductModel());
        tableProductScroll.setViewportView(tableProduct);
        ListSelectionModel selectionModelProduct = tableProduct.getSelectionModel();
        selectionModelProduct.addListSelectionListener(listSelectionEvent -> prepareToEditProduct());
        addUnpickProductEscape();
    }

    private void removeFocusFromTableStore() {
        tableStoreScroll.setViewportView(null);

        tableStore = initializer.newTable(setInitialTableStoreModel());
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
            ArrayList<Product> productsWithThisName;
            try (SqlSession session = DbSession.startSession()) {
                StoreMapper mapper = session.getMapper(StoreMapper.class);
                productsWithThisName = mapper.getProductsByName(tfName.getText());
            }

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
                try (SqlSession session = DbSession.startSession()) {
                    StoreMapper mapper = session.getMapper(StoreMapper.class);
                    mapper.insertProduct(
                            tfName.getText(),
                            textAreaCharacteristics.getText(),
                            (Integer) spinnerCost.getValue(),
                            cbbxCategory.getSelectedIndex() + 1
                    );
                    session.commit();
                }
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
            try (SqlSession session = DbSession.startSession()) {
                StoreMapper mapper = session.getMapper(StoreMapper.class);

                mapper.updateProduct(
                        currentProduct.getID(),
                        tfName.getText(),
                        textAreaCharacteristics.getText(),
                        (Integer) spinnerCost.getValue(),
                        cbbxCategory.getSelectedIndex() + 1
                );

                session.commit();
            }
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
            try (SqlSession session = DbSession.startSession()) {
                StoreMapper mapper = session.getMapper(StoreMapper.class);
                mapper.deleteProduct(currentProduct.getID());
                session.commit();
            }

            JOptionPane.showMessageDialog(
                    frame,
                    "Товар успешно удалён.",
                    "Удаление завершено",
                    JOptionPane.INFORMATION_MESSAGE
            );
            currentProduct = null;
            unpickProduct();
        }
    }

    private void addToStore() {
        ArrayList<ProductToStore> currentProductOnStore;
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            currentProductOnStore = mapper.getProductToStore(currentProduct.getID(), currentStore.getID());
        }

        if (currentProductOnStore.size() != 0) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Товар уже существует на складе.",
                    "Ошибка при добавлении",
                    JOptionPane.WARNING_MESSAGE
            );
        } else {
            try (SqlSession session = DbSession.startSession()) {
                StoreMapper mapper = session.getMapper(StoreMapper.class);
                mapper.insertProductToStore(currentStore.getID(), currentProduct.getID(), 1);
                session.commit();
            }

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
            try (SqlSession session = DbSession.startSession()) {
                StoreMapper mapper = session.getMapper(StoreMapper.class);
                mapper.deleteProductFromStore(currentProductToStore.getID());
                session.commit();
            }
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

        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            mapper.updateCount(currentProductToStore.getID(), currentProductToStore.getCount());
            session.commit();
        }
        removeFocusFromTableStore();
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

            try (SqlSession session = DbSession.startSession()) {
                StoreMapper mapper = session.getMapper(StoreMapper.class);
                mapper.updateCount(currentProductToStore.getID(), currentProductToStore.getCount());
                session.commit();
            }
            removeFocusFromTableStore();
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
            if ((currentProductToStore.getCount() - (int) spinnerMoveProduct.getValue() < 0)) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Вы не можете отпустить такое количество товаров.",
                        "Ошибка при изменении",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if ((int) spinnerMoveProduct.getValue() > 0) {
                ArrayList<ProductToStore> productsInOtherStory;

                try (SqlSession session = DbSession.startSession()) {
                    StoreMapper mapper = session.getMapper(StoreMapper.class);
                    productsInOtherStory = mapper.getProductToStore(
                            currentProductToStore.getProduct_ID(),
                            mapper.getStoreIDByName(cbbxOtherStores.getSelectedItem().toString())
                    );
                }

                if (productsInOtherStory.size() != 0) {
                    try (SqlSession session = DbSession.startSession()) {
                        StoreMapper mapper = session.getMapper(StoreMapper.class);
                        mapper.updateCount(
                                productsInOtherStory.get(0).getID(),
                                productsInOtherStory.get(0).getCount() + (int) spinnerMoveProduct.getValue()
                        );
                        mapper.updateCount(currentProductToStore.getID(), currentProductToStore.getCount() - (int) spinnerMoveProduct.getValue());
                        session.commit();
                    }
                } else {
                    try (SqlSession session = DbSession.startSession()) {
                        StoreMapper mapper = session.getMapper(StoreMapper.class);
                        mapper.insertProductToStore(
                                mapper.getStoreIDByName(cbbxOtherStores.getSelectedItem().toString()),
                                currentProductToStore.getProduct_ID(),
                                (int) spinnerMoveProduct.getValue()
                        );
                        mapper.updateCount(currentProductToStore.getID(), currentProductToStore.getCount() - (int) spinnerMoveProduct.getValue());
                        session.commit();
                    }
                }
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

        ArrayList<ProductView> result;

        if (rbName.isSelected()) {
            column = rbName.getActionCommand();
            option = rbName.getText();
        } else if (rbCategory.isSelected()) {
            column = rbCategory.getActionCommand();
            option = rbCategory.getText();
        }

        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            result = mapper.searchProductView(column, query);
        }

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

        ArrayList<StoreView> result;

        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            result = mapper.searchStoreView(currentStore.getID(), query);
        }

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
