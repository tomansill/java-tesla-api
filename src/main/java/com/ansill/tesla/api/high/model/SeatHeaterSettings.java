package com.ansill.tesla.api.high.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Optional;

@Immutable
public final class SeatHeaterSettings{

    /** heating level on front left seat (drivers seat in RHD configuration) */
    @Nullable
    private final Integer frontLeftLevel;

    /** heating level on front right seat (passengers seat in RHD configuration) */
    @Nullable
    private final Integer frontRightLevel;

    /** heating level on middle left seat */
    @Nullable
    private final Integer middleLeftLevel;

    /** heating level on middle center seat */
    @Nullable
    private final Integer middleCenterLevel;

    /** heating level on middle right seat */
    @Nullable
    private final Integer middleRightLevel;

    /** heating level on rear left seat */
    @Nullable
    private final Integer rearLeftLevel;

    /** heating level on rear right seat */
    @Nullable
    private final Integer rearRightLevel;

    /**
     * SeatHeaterSettings constructor
     *
     * @param frontLeftLevel    heating level on front left seat (drivers seat in RHD configuration)
     * @param frontRightLevel   heating level on front right seat (passengers seat in RHD configuration)
     * @param middleLeftLevel   heating level on middle left seat
     * @param middleCenterLevel heating level on middle center seat
     * @param middleRightLevel  heating level on middle right seat
     * @param rearLeftLevel     heating level on rear left seat
     * @param rearRightLevel    heating level on rear right seat
     */
    private SeatHeaterSettings(
            @Nullable Integer frontLeftLevel,
            @Nullable Integer frontRightLevel,
            @Nullable Integer middleLeftLevel,
            @Nullable Integer middleCenterLevel,
            @Nullable Integer middleRightLevel,
            @Nullable Integer rearLeftLevel,
            @Nullable Integer rearRightLevel
    ){
        this.frontLeftLevel = frontLeftLevel;
        this.frontRightLevel = frontRightLevel;
        this.middleLeftLevel = middleLeftLevel;
        this.middleCenterLevel = middleCenterLevel;
        this.middleRightLevel = middleRightLevel;
        this.rearLeftLevel = rearLeftLevel;
        this.rearRightLevel = rearRightLevel;
    }

    /**
     * Converts medium-level to high-level object
     *
     * @param state medium level object
     * @return high-level object
     */
    @Nonnull
    public static SeatHeaterSettings convert(@Nonnull com.ansill.tesla.api.med.model.ClimateState state){
        return new SeatHeaterSettings(
                state.getSeatHeaterLeft(),
                state.getSeatHeaterRight(),
                state.getSeatHeaterRearLeft(),
                state.getSeatHeaterRearCenter(),
                state.getSeatHeaterRearRight(),
                state.getSeatHeaterLeftBack(),
                state.getSeatHeaterRightBack()
        );
    }

    /**
     * Returns front left seat's heating level
     *
     * @return level
     */
    @Nonnull
    public Optional<Integer> getFrontLeftLevel(){
        return Optional.ofNullable(frontLeftLevel);
    }

    /**
     * Returns front right seat's heating level
     *
     * @return level
     */
    @Nonnull
    public Optional<Integer> getFrontRightLevel(){
        return Optional.ofNullable(frontRightLevel);
    }

    /**
     * Returns middle left seat's heating level
     *
     * @return level
     */
    @Nonnull
    public Optional<Integer> getMiddleLeftLevel(){
        return Optional.ofNullable(middleLeftLevel);
    }

    /**
     * Returns middle center seat's heating level
     *
     * @return level
     */
    @Nonnull
    public Optional<Integer> getMiddleCenterLevel(){
        return Optional.ofNullable(middleCenterLevel);
    }

    /**
     * Returns middle right seat's heating level
     *
     * @return level
     */
    @Nonnull
    public Optional<Integer> getMiddleRightLevel(){
        return Optional.ofNullable(middleRightLevel);
    }

    /**
     * Returns rear left seat's heating level
     *
     * @return level
     */
    @Nonnull
    public Optional<Integer> getRearLeftLevel(){
        return Optional.ofNullable(rearLeftLevel);
    }

    /**
     * Returns rear right seat's heating level
     *
     * @return level
     */
    @Nonnull
    public Optional<Integer> getRearRightLevel(){
        return Optional.ofNullable(rearRightLevel);
    }
}
