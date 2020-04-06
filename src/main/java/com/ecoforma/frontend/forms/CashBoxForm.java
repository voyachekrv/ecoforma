package com.ecoforma.frontend.forms;

import com.ecoforma.db.entities.ProductOnCashBox;
import com.ecoforma.db.services.SaleService;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.ecoforma.App.COMPANY_NAME;
import static com.ecoforma.App.saleForm;

import static com.ecoforma.frontend.services.JComponentFactory.*;

public class CashBoxForm {
    private final char CURRENCY = '₽';

    JFrame frame;
    JScrollPane tableScroll;
    JTable table;
    JTextField tfSearch;
    JButton btnSearch, btnStopSearch;
    JRadioButton rbName, rbCategory;
    JComboBox cbbxStores, cbbxPaymentTypes;
    JLabel lEmployeeName, lProductNameVal, lProductCategoryVal, lStoreNameVal, lStoreAddressVal, lStorePhoneVal, lStoreChiefName;
    JLabel lCustomerNameVal, lCustomerAddressVal, lCustomerPhoneVal;
    JButton btnPickCustomer;
    JSpinner spinnerCount, spinnerPrepayment, spinnerDiscount;
    JCheckBox cbFullPayment, cbGiveDiscount;
    JButton btnCountFinalPayment;
    JLabel lSumProductVal, lSumCategoryVal, lSumCountVal;
    JLabel lSumPaymentVal, lSumDiscountVal, lSumWithDiscountVal, lSumPrepaymentVal;
    JLabel lSumWithPrepaymentVal, lSumToPaymentVal;
    JButton btnAcceptOrder, btnCancelOrder;

    private String[] columnsHeader = new String[] {
            "Код записи о хранении",
            "Код товара",
            "Название товара",
            "Категория",
            "Цена",
            "Склад",
            "Количество на складе"
    };

    SaleService dbService;

    public CashBoxForm() {
        dbService = new SaleService();

        frame = newFrame(COMPANY_NAME + " - Касса", new Rectangle(323,  144, 1352, 790), JFrame.DO_NOTHING_ON_CLOSE);

        JToolBar toolBar = newToolBar(0, 0, 1356,44);
        frame.add(toolBar);

        JButton btnClose = newButtonEnabled("Закрыть", "icon-logout", new Rectangle(1, 1, 1, 1));
        toolBar.add(btnClose);

        JPanel panelProducts = newPanelBevelTable(12, 115, 670, 634);
        frame.add(panelProducts);

        table = newTable(setTableModel());

        tableScroll = newTableScroll(table, 646,  620);
        panelProducts.add(tableScroll);

        tfSearch = newTextFieldEnabled(50, new Rectangle(12, 55, 180, 23));
        frame.add(tfSearch);

        btnSearch = newButtonEnabled("Поиск", "icon-search", new Rectangle(204, 55, 98, 23));
        frame.add(btnSearch);

        btnStopSearch = newButtonEnabled(null, "icon-close", new Rectangle(315, 55, 24, 23));
        frame.add(btnStopSearch);

        JLabel lSearchOptions = newLabel("Критерии поиска:", new Rectangle(12, 82, 107, 24));
        frame.add(lSearchOptions);

        rbName = newRadioButton("Название", "Поменять потом", new Rectangle(121, 82, 81, 24));
        rbName.setSelected(true);
        frame.add(rbName);

        rbCategory = newRadioButton("Категория", "Поменять потом", new Rectangle(214, 82, 84, 24));
        frame.add(rbCategory);

        ButtonGroup searchGroup = new ButtonGroup();
        searchGroup.add(rbName);
        searchGroup.add(rbCategory);

        JLabel lStoreComboBox = newLabel("Склад:", new Rectangle(324, 82, 50, 24));
        frame.add(lStoreComboBox);

        cbbxStores = newComboBox(setComboBoxStoresModel(), new Rectangle(372, 82, 286, 24));
        cbbxStores.setEnabled(true);
        frame.add(cbbxStores);

        JPanel panelEmployeeInformation = newPanelEtched(694, 55, 635, 49);
        frame.add(panelEmployeeInformation);

        JLabel lEmployeeInfo = newLabel("Менеджер по продажам:", new Rectangle(12, 12, 148, 25));
        panelEmployeeInformation.add(lEmployeeInfo);

        lEmployeeName = newLabelColored("", new Rectangle(172, 13, 267, 20));
        panelEmployeeInformation.add(lEmployeeName);

        JPanel panelDate = newPanelEtchedRaised(448, 12, 175, 25);
        panelEmployeeInformation.add(panelDate);

        SimpleDateFormat formatForDateNow = new SimpleDateFormat("d MMMM yyyy г.");
        JLabel lDate = newLabelBigFont(formatForDateNow.format(new Date()), new Rectangle(12, 0, 151, 25));
        panelDate.add(lDate);

        JPanel panelProductInfo = newPanelEtched(694, 110, 635, 166);
        frame.add(panelProductInfo);

        JLabel lProductName = newLabel("Название выбранного товара:", new Rectangle(12, 12, 214, 23));
        panelProductInfo.add(lProductName);

        JLabel lProductCategory = newLabel("Категория товара:", new Rectangle(238, 12, 162, 23));
        panelProductInfo.add(lProductCategory);

        JLabel lStore = newLabel("Склад:", new Rectangle(412, 12, 59, 23));
        panelProductInfo.add(lStore);

        lProductNameVal = newLabelBigFont("", new Rectangle(12, 35, 214, 23));
        panelProductInfo.add(lProductNameVal);

        lProductCategoryVal = newLabelBigFont("", new Rectangle(238, 35, 162, 23));
        panelProductInfo.add(lProductCategoryVal);

        lStoreNameVal = newLabelBigFont("", new Rectangle(412, 36, 211, 20));
        panelProductInfo.add(lStoreNameVal);

        lStoreAddressVal = newLabelBigFont("", new Rectangle(412, 68, 211, 20));
        panelProductInfo.add(lStoreAddressVal);

        lStorePhoneVal = newLabelBigFont("", new Rectangle(412, 100, 207, 16));
        panelProductInfo.add(lStorePhoneVal);

        lStoreChiefName = newLabelBigFont("", new Rectangle(412, 128, 211, 16));
        panelProductInfo.add(lStoreChiefName);

        JPanel panelCustomerInfo = newPanelEtched(694, 284, 635, 111);
        frame.add(panelCustomerInfo);

        JLabel lCustomerName = newLabel("Заказчик:", new Rectangle(12, 12, 68, 20));
        panelCustomerInfo.add(lCustomerName);

        JLabel lCustomerAddress = newLabel("Адрес доставки:", new Rectangle(235, 12, 100, 20));
        panelCustomerInfo.add(lCustomerAddress);

        JLabel lCustomerPhone = newLabel("Телефон:", new Rectangle(473, 12, 100, 20));
        panelCustomerInfo.add(lCustomerPhone);

        lCustomerNameVal = newLabelBigFont("", new Rectangle(12, 38, 219, 20));
        panelCustomerInfo.add(lCustomerNameVal);

        lCustomerAddressVal = newLabelBigFont("", new Rectangle(235, 38, 226, 20));
        panelCustomerInfo.add(lCustomerAddressVal);

        lCustomerPhoneVal = newLabelBigFont("", new Rectangle(473, 40, 150, 16));
        panelCustomerInfo.add(lCustomerPhoneVal);

        btnPickCustomer = newButtonEnabled("Выбор заказчика", new Rectangle(483, 73, 140, 26));
        panelCustomerInfo.add(btnPickCustomer);

        JLabel lProductCount = newLabel("Количество единиц товара, шт.", new Rectangle(694, 407, 192, 20));
        frame.add(lProductCount);

        JLabel lPrepayment = newLabel("Предоплата, %", new Rectangle(898, 407, 97, 20));
        frame.add(lPrepayment);

        JLabel lPaymentType = newLabel("Способ оплаты", new Rectangle(1123, 407, 206, 20));
        frame.add(lPaymentType);

        spinnerCount = newSpinnerNumericEnabled(
            new SpinnerNumberModel(0, 0, 99999, 1), 
            new Rectangle(694, 439, 181, 20)
        );
        frame.add(spinnerCount);

        spinnerPrepayment = newSpinnerNumericEnabled(
            new SpinnerNumberModel(40, 0, 100, 1), 
            new Rectangle(898, 439, 97, 20)
        );
        frame.add(spinnerPrepayment);

        cbFullPayment = newCheckBox("Полная сумма", new Rectangle(1003, 437, 112, 24));
        frame.add(cbFullPayment);

        cbbxPaymentTypes = newComboBox(dbService.getPaymentTypes(), new Rectangle(1123, 437, 206, 25));
        cbbxPaymentTypes.setEnabled(true);
        frame.add(cbbxPaymentTypes);

        JLabel lDiscount = newLabel("Скидка, %", new Rectangle(694, 471, 70, 20));
        frame.add(lDiscount);

        spinnerDiscount = newSpinnerNumericEnabled(
            new SpinnerNumberModel(0, 0, 100, 1), 
            new Rectangle(768, 471, 107, 20)
        );
        frame.add(spinnerDiscount);

        cbGiveDiscount = newCheckBox("Предоставить скидку", new Rectangle(883, 467, 151, 24));
        frame.add(cbGiveDiscount);

        btnCountFinalPayment = newButtonEnabled("Расчёт стоимости итого", new Rectangle(1148, 474, 181, 26));
        frame.add(btnCountFinalPayment);

        JPanel panelSummary = newPanelTitled("Итого", new Rectangle(694, 503, 635, 173));
        frame.add(panelSummary);

        JLabel lSumProduct = newLabel("Товар:", new Rectangle(12, 27, 48, 20));
        panelSummary.add(lSumProduct);

        JLabel lSumCategory = newLabel("Категория:", new Rectangle(238, 27, 162, 20));
        panelSummary.add(lSumCategory);

        JLabel lSumCount = newLabel("Количество единиц товара, шт.:", new Rectangle(412, 27, 192, 20));
        panelSummary.add(lSumCount);

        lSumProductVal = newLabelBigFont("", new Rectangle(12, 47, 214, 23));
        panelSummary.add(lSumProductVal);

        lSumCategoryVal = newLabelBigFont("", new Rectangle(248, 47, 162, 23));
        panelSummary.add(lSumCategoryVal);

        lSumCountVal = newLabelBigFont("", new Rectangle(410, 48, 162, 20));
        panelSummary.add(lSumCountVal);

        JLabel lSumPayment = newLabel("Общая стоимость", new Rectangle(12, 75, 113, 20));
        panelSummary.add(lSumPayment);

        JLabel lSumDiscount = newLabel("Скидка, %:", new Rectangle(137, 75, 70, 20));
        panelSummary.add(lSumDiscount);

        JLabel lSumWithDiscount = newLabel("Стоимость с учётом скидки:", new Rectangle(237, 75, 163, 20));
        panelSummary.add(lSumWithDiscount);

        JLabel lSumPrepayment = newLabel("Предоплата, %:", new Rectangle(412, 75, 102, 20));
        panelSummary.add(lSumPrepayment);

        lSumPaymentVal = newLabelBigFont("", new Rectangle(12, 96, 102, 23));
        panelSummary.add(lSumPaymentVal);

        lSumDiscountVal = newLabelBigFont("", new Rectangle(137, 96, 61, 23));
        panelSummary.add(lSumDiscountVal);

        lSumWithDiscountVal = newLabelBigFont("", new Rectangle(238, 97, 162, 20));
        panelSummary.add(lSumWithDiscountVal);

        lSumPrepaymentVal = newLabelBigFont("", new Rectangle(412, 96, 61, 23));
        panelSummary.add(lSumPrepaymentVal);

        JLabel lSumWithPrepayment = newLabel("Стоимость с учётом предоплаты:", new Rectangle(12, 118, 199, 20));
        panelSummary.add(lSumWithPrepayment);

        JLabel lSumToPayment = newLabel("К оплате:", new Rectangle(238, 120, 70, 37));
        panelSummary.add(lSumToPayment);

        lSumWithPrepaymentVal = newLabelBigFont("", new Rectangle(12, 138, 195, 23));
        panelSummary.add(lSumWithPrepaymentVal);

        lSumToPaymentVal = newLabelFinalPrice("", new Rectangle(307, 118, 192, 37));
        panelSummary.add(lSumToPaymentVal);

        btnAcceptOrder = newButtonEnabled("Оформить заказ", new Rectangle(1015, 683, 151, 26));
        frame.add(btnAcceptOrder);

        btnCancelOrder = newButtonEnabled("Отменить заказ", new Rectangle(1178, 683, 151, 26));
        frame.add(btnCancelOrder);

        frame.setVisible(true);

        btnClose.addActionListener(actionEvent -> closeForm());
    }

    @NotNull
    private DefaultTableModel setTableModel() {
        DefaultTableModel initialTableModel = new DefaultTableModel();
        initialTableModel.setColumnIdentifiers(columnsHeader);

        ArrayList<ProductOnCashBox> result = dbService.getAllProductsOnCashBox();

        for (int i = 0; i < result.size(); i++) {
            initialTableModel.insertRow(i, new Object[] {
                    result.get(i).getProductOnStoreID(),
                    result.get(i).getProductID(),
                    result.get(i).getProductName(),
                    result.get(i).getCategoryName(),
                    result.get(i).getCost(),
                    result.get(i).getStoreName(),
                    result.get(i).getCount()
            });
        }

        return initialTableModel;
    }

    @NotNull
    private String[] setComboBoxStoresModel() {
        ArrayList<String> comboBoxModel = new ArrayList<>();
        comboBoxModel.add("Все склады");

        ArrayList<String> result = dbService.getStoreNames();
        comboBoxModel.addAll(result);

        Object[] resultArray = result.toArray();
        String[] comboBoxModelArray = new String[result.size() + 1];
        comboBoxModelArray[0] = "Все склады";

        for (int i = 1; i < result.size() + 1; i++) {
            comboBoxModelArray[i] = resultArray[i - 1].toString();
        }

        return comboBoxModelArray;
    }

    private void closeForm() {
        frame.setVisible(false);
        frame = null;
        saleForm.frame.setExtendedState(JFrame.NORMAL);
    }
}
