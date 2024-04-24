package moim_today.presentation.moim;

import moim_today.fake_class.moim.FakeMoimService;
import moim_today.util.ControllerTest;

class MoimControllerTest extends ControllerTest {

    private final FakeMoimService fakeMoimService = new FakeMoimService();

    @Override
    protected Object initController() {
        return new MoimController(fakeMoimService);
    }

}