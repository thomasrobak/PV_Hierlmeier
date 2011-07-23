package hierlmeier



import org.junit.*
import grails.test.mixin.*


@TestFor(TierController)
@Mock(Tier)
class TierControllerTests {


    @Test
    void testIndex() {
        controller.index()
        assert "/tier/list" == response.redirectedUrl
    }

    @Test
    void testList() {

        def model = controller.list()

        assert model.tierInstanceList.size() == 0
        assert model.tierInstanceTotal == 0

    }

    @Test
    void testCreate() {
       def model = controller.create()

       assert model.tierInstance != null


    }

    @Test
    void testSave() {
        controller.save()

        assert model.tierInstance != null
        assert view == '/tier/create'

        // TODO: Populate valid properties

        controller.save()

        assert response.redirectedUrl == '/tier/show/1'
        assert controller.flash.message != null
        assert Tier.count() == 1
    }


    @Test
    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/tier/list'


        def tier = new Tier()

        // TODO: populate domain properties

        assert tier.save() != null

        params.id = tier.id

        def model = controller.show()

        assert model.tierInstance == tier
    }

    @Test
    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/tier/list'


        def tier = new Tier()

        // TODO: populate valid domain properties

        assert tier.save() != null

        params.id = tier.id

        def model = controller.edit()

        assert model.tierInstance == tier
    }

    @Test
    void testUpdate() {

        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/tier/list'

        response.reset()


        def tier = new Tier()

        // TODO: populate valid domain properties

        assert tier.save() != null

        // test invalid parameters in update
        params.id = tier.id

        controller.update()

        assert view == "/tier/edit"
        assert model.tierInstance != null

        tier.clearErrors()

        // TODO: populate valid domain form parameter
        controller.update()

        assert response.redirectedUrl == "/tier/show/$tier.id"
        assert flash.message != null
    }

    @Test
    void testDelete() {
        controller.delete()

        assert flash.message != null
        assert response.redirectedUrl == '/tier/list'

        response.reset()

        def tier = new Tier()

        // TODO: populate valid domain properties
        assert tier.save() != null
        assert Tier.count() == 1

        params.id = tier.id

        controller.delete()

        assert Tier.count() == 0
        assert Tier.get(tier.id) == null
        assert response.redirectedUrl == '/tier/list'


    }


}