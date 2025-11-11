package cn.mangofanfan.fanwindow.client.screen;

import cn.mangofanfan.fanwindow.client.screen.widget.GameModeCardWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.Difficulty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Environment(EnvType.CLIENT)
public class NewCreateWorldScreenGameTab extends GridScreenTab {
    private static final Text GAME_TAB_TITLE_TEXT = Text.translatable("createWorld.tab.game.title");
    private static final Text ALLOW_COMMANDS_TEXT = Text.translatable("selectWorld.allowCommands");
    private final TextFieldWidget worldNameField;
    static final Text ENTER_NAME_TEXT = Text.translatable("selectWorld.enterName");
    static final Text EXPERIMENTS_TEXT = Text.translatable("selectWorld.experiments");
    static final Text ALLOW_COMMANDS_INFO_TEXT = Text.translatable("selectWorld.allowCommands.info");

    Logger logger = LoggerFactory.getLogger(NewCreateWorldScreenGameTab.class);

    public NewCreateWorldScreenGameTab(CreateWorldScreen self) {
        super(GAME_TAB_TITLE_TEXT);
        GridWidget.Adder adder = this.grid.setRowSpacing(8).createAdder(1);
        Positioner positioner = adder.copyPositioner();
        WorldCreator worldCreator = self.getWorldCreator();
        this.worldNameField = new TextFieldWidget(self.getTextRenderer(), 208, 20, Text.translatable("selectWorld.enterName"));
        this.worldNameField.setText(worldCreator.getWorldName());
        this.worldNameField.setChangedListener(worldCreator::setWorldName);
        worldCreator
                .addListener(
                        creator -> this.worldNameField
                                .setTooltip(
                                        Tooltip.of(Text.translatable("selectWorld.targetFolder", Text.literal(creator.getWorldDirectoryName()).formatted(Formatting.ITALIC)))
                                )
                );
        // CreateWorldScreen.this.setInitialFocus(this.worldNameField);
        adder.add(
                LayoutWidgets.createLabeledWidget(self.getTextRenderer(), this.worldNameField, ENTER_NAME_TEXT),
                adder.copyPositioner().alignHorizontalCenter()
        );

        // 新版游戏模式选择器
        DirectionalLayoutWidget directionalLayoutWidget_GameMod = DirectionalLayoutWidget.horizontal().spacing(16);
        GameModeCardWidget tab_GameModeSurvival = directionalLayoutWidget_GameMod.add(new GameModeCardWidget(worldCreator, self));
        tab_GameModeSurvival.setMode(WorldCreator.Mode.SURVIVAL);
        GameModeCardWidget tab_GameModeHardcore = directionalLayoutWidget_GameMod.add(new GameModeCardWidget(worldCreator, self));
        tab_GameModeHardcore.setMode(WorldCreator.Mode.HARDCORE);
        GameModeCardWidget tab_GameModeCreative = directionalLayoutWidget_GameMod.add(new GameModeCardWidget(worldCreator, self));
        tab_GameModeCreative.setMode(WorldCreator.Mode.CREATIVE);
        adder.add(directionalLayoutWidget_GameMod, positioner.alignHorizontalCenter());

        GameModeCardWidget[] tab_GameModes = {tab_GameModeSurvival, tab_GameModeHardcore, tab_GameModeCreative};
        worldCreator.addListener(creator -> {
            for(GameModeCardWidget tab_GameMode : tab_GameModes) {
                tab_GameMode.setChosen(false);
                if (tab_GameMode.getMode().equals(worldCreator.getGameMode())) {
                    tab_GameMode.setChosen(true);
                }
            }
        });

        // 游戏难度和允许作弊按钮调整在一行内
        DirectionalLayoutWidget directionalLayoutWidget_Settings = DirectionalLayoutWidget.horizontal().spacing(8);

        CyclingButtonWidget<Difficulty> cyclingButtonWidget_Difficulty = directionalLayoutWidget_Settings.add(
                CyclingButtonWidget.builder(Difficulty::getTranslatableName)
                        .values(Difficulty.values())
                        .build(0, 0, 150, 20, Text.translatable("options.difficulty"), (button, value) -> worldCreator.setDifficulty(value)),
                positioner
        );
        worldCreator.addListener(creator -> {
            cyclingButtonWidget_Difficulty.setValue(creator.getDifficulty());
            cyclingButtonWidget_Difficulty.active = !creator.isHardcore();
            cyclingButtonWidget_Difficulty.setTooltip(Tooltip.of(creator.getDifficulty().getInfo()));
        });
        CyclingButtonWidget<Boolean> cyclingButtonWidget_AllowCheat = directionalLayoutWidget_Settings.add(
                CyclingButtonWidget.onOffBuilder()
                        .tooltip(value -> Tooltip.of(ALLOW_COMMANDS_INFO_TEXT))
                        .build(0, 0, 150, 20, ALLOW_COMMANDS_TEXT, (button, value) -> worldCreator.setCheatsEnabled(value))
        );
        worldCreator.addListener(creator -> {
            cyclingButtonWidget_AllowCheat.setValue(worldCreator.areCheatsEnabled());
            cyclingButtonWidget_AllowCheat.active = !worldCreator.isDebug() && !worldCreator.isHardcore();
        });

        adder.add(directionalLayoutWidget_Settings, positioner.alignHorizontalCenter());

        if (!SharedConstants.getGameVersion().stable()) {
            adder.add(
                    ButtonWidget.builder(
                                    EXPERIMENTS_TEXT,
                                    button -> self.openExperimentsScreen(worldCreator.getGeneratorOptionsHolder().dataConfiguration())
                            )
                            .width(210)
                            .build()
            );
        }

        logger.info("New Create World Screen Game Tab initialized.");
    }
}
